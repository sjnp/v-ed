resource "aws_vpc" "prod_vpc" {
  cidr_block = var.aws_vpc_cidr

  tags = {
    Name = "ved_prod"
  }
}

resource "aws_internet_gateway" "prod_igw" {
  vpc_id = aws_vpc.prod_vpc.id

  tags = {
    Name = "ved_prod"
  }
}

resource "aws_route_table" "prod_route_table" {
  vpc_id = aws_vpc.prod_vpc.id

  route  {
    cidr_block = var.aws_internet_cidr
    gateway_id = aws_internet_gateway.prod_igw.id
  } 

  tags = {
    Name = "ved_prod"
  }
}

resource "aws_subnet" "prod_subnet" {
  vpc_id     = aws_vpc.prod_vpc.id
  cidr_block = var.aws_subnet_cidr
  availability_zone = var.aws_availability_zone

  tags = {
    Name = "ved_prod"
  }
}

resource "aws_route_table_association" "prod_route_assoc" {
  subnet_id = aws_subnet.prod_subnet.id
  route_table_id = aws_route_table.prod_route_table.id
}

resource "aws_security_group" "prod_sg" {
  name = var.aws_security_group.name
  description = var.aws_security_group.description
  vpc_id = aws_vpc.prod_vpc.id

  ingress {
    description = "HTTPS"
    cidr_blocks = [var.aws_internet_cidr]
    from_port = var.aws_ports.HTTPS
    to_port = var.aws_ports.HTTPS
    protocol = "tcp"
  } 

  ingress {
    description = "HTTP"
    cidr_blocks = [var.aws_internet_cidr]
    from_port = var.aws_ports.HTTP
    to_port = var.aws_ports.HTTP
    protocol = "tcp"
  }

  ingress {
    description = "SSH"
    cidr_blocks = [var.aws_internet_cidr]
    from_port = var.aws_ports.SSH
    to_port = var.aws_ports.SSH
    protocol = "tcp"
  }
  
  egress {
    cidr_blocks = [var.aws_internet_cidr]
    from_port = 0
    to_port = 0
    protocol = "-1"
  }

  tags = {
    Name = "ved_prod"
  }
}

resource "aws_network_interface" "prod_nic" {
  subnet_id = aws_subnet.prod_subnet.id
  private_ips = [var.aws_private_ip]
  security_groups = [aws_security_group.prod_sg.id]
}

resource "aws_eip" "prod_eip" {
  vpc = true
  network_interface = aws_network_interface.prod_nic.id
  associate_with_private_ip = var.aws_private_ip
  depends_on = [
    aws_internet_gateway.prod_igw
  ]
}

resource "aws_instance" "prod_ec2" {
  instance_type = var.aws_instance_type
  ami = data.aws_ami.prod_ami.id
  availability_zone = var.aws_availability_zone
  key_name = var.aws_key_pair

  network_interface {
    device_index = 0
    network_interface_id = aws_network_interface.prod_nic.id
  }

  user_data = templatefile("userdata.tpl", {})

  tags = {
    Name = "ved_prod"
  }
}