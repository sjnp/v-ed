data "aws_ami" "prod_ami" {
  most_recent = true
  owners = [var.aws_ami.owner]

  filter {
    name = "name"
    values = [var.aws_ami.name]
  }
}
