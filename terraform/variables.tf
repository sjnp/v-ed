variable "aws_region" {
  type = string
  default = "ap-southeast-1"
}

variable "aws_access_key" {
  type = string
}

variable "aws_secret_key" {
  type = string
}

variable "aws_vpc_cidr" {
  type = string
  default = "10.0.0.0/16"
}

variable "aws_subnet_cidr" {
  type = string
  default = "10.0.1.0/24"
}

variable "aws_internet_cidr" {
  type = string
  default = "0.0.0.0/0"
}

variable "aws_availability_zone" {
  type = string
  default = "ap-southeast-1a"
}

variable "aws_security_group" {
  type = map(string)
  default = {
    name = "ved_public_sg"
    description = "Default public security group for v-ed"
  }
}

variable "aws_ports" {
  type = map(number)
  default = {
    HTTPS = 443
    HTTP = 80
    SSH = 22
  }
}

variable "aws_private_ip" {
  type = string
  default = "10.0.1.50"
}

variable "aws_instance_type" {
  type = string
  default = "t2.micro"
}

variable "aws_ami" {
  type = map(string)
  default = {
    owner = "099720109477"
    name = "ubuntu/images/hvm-ssd/ubuntu-jammy-22.04-amd64-server-*"
  }
}

variable "aws_key_pair" {
  type = string
}