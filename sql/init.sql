-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema dojo_jpa
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema dojo_jpa
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `dojo_jpa` DEFAULT CHARACTER SET utf8 ;
USE `dojo_jpa` ;

-- -----------------------------------------------------
-- Table `dojo_jpa`.`usuarios`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dojo_jpa`.`usuarios` ;

CREATE TABLE IF NOT EXISTS `dojo_jpa`.`usuarios` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dojo_jpa`.`vendas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dojo_jpa`.`vendas` ;

CREATE TABLE IF NOT EXISTS `dojo_jpa`.`vendas` (
  `id`  INT NOT NULL AUTO_INCREMENT,
  `data_registro` TIMESTAMP NULL,
  `data_atualizacao` TIMESTAMP NULL,
  `usuario_id` INT NOT NULL,
  `valor` DECIMAL(13,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_vendas_usuarios1_idx` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_vendas_usuarios1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `dojo_jpa`.`usuarios` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dojo_jpa`.`produtos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dojo_jpa`.`produtos` ;

CREATE TABLE IF NOT EXISTS `dojo_jpa`.`produtos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255)  NOT NULL,
  `descricao` VARCHAR(255)  NOT NULL,
  `preco` DECIMAL(13,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dojo_jpa`.`vendas_itens`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dojo_jpa`.`vendas_itens` ;

CREATE TABLE IF NOT EXISTS `dojo_jpa`.`vendas_itens` (
  `venda_id` INT NOT NULL,
  `produto_id` INT NOT NULL,
  `quantidade` INT NOT NULL DEFAULT 0,
  `data_registro` TIMESTAMP NOT NULL DEFAULT current_timestamp,
  `valor` DECIMAL(13,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (`venda_id`, `produto_id`),
  INDEX `fk_vendas-itens_produtos1_idx` (`produto_id` ASC) VISIBLE,
  CONSTRAINT `fk_vendas-itens_vendas`
    FOREIGN KEY (`venda_id`)
    REFERENCES `dojo_jpa`.`vendas` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_vendas-itens_produtos1`
    FOREIGN KEY (`produto_id`)
    REFERENCES `dojo_jpa`.`produtos` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dojo_jpa`.`permissoes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dojo_jpa`.`permissoes` ;

CREATE TABLE IF NOT EXISTS `dojo_jpa`.`permissoes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `dojo_jpa`.`permissoes_usuarios`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dojo_jpa`.`permissoes_usuarios` ;

CREATE TABLE IF NOT EXISTS `dojo_jpa`.`permissoes_usuarios` (
  `permissao_id` INT NOT NULL,
  `usuario_id` INT NOT NULL,
  PRIMARY KEY (`permissao_id`, `usuario_id`),
  INDEX `fk_permissoes_has_usuarios_usuarios1_idx` (`usuario_id` ASC) VISIBLE,
  INDEX `fk_permissoes_has_usuarios_permissoes1_idx` (`permissao_id` ASC) VISIBLE,
  CONSTRAINT `fk_permissoes_has_usuarios_permissoes1`
    FOREIGN KEY (`permissao_id`)
    REFERENCES `dojo_jpa`.`permissoes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_permissoes_has_usuarios_usuarios1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `dojo_jpa`.`usuarios` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


## Populando base

INSERT into dojo_jpa.permissoes values(null, "SALVAR-VENDA");
INSERT into dojo_jpa.permissoes values(null, "SALVAR-PRODUTO");

insert into dojo_jpa.usuarios values(null, 'Fred');
insert into dojo_jpa.usuarios values(null, 'Caio');
insert into dojo_jpa.permissoes_usuarios values (1, 1);
insert into dojo_jpa.permissoes_usuarios values (1, 2);
insert into dojo_jpa.permissoes_usuarios values (2, 1);
insert into dojo_jpa.permissoes_usuarios values (2, 2);


insert into dojo_jpa.produtos values(null, 'Refrigerante 1L', 'Bebida gaseificada sabor guaraná 1L', 10);
insert into dojo_jpa.produtos values(null, 'Farinha de trigo 1kg', 'Farinha branca de alta qualidade, muito versátil.', 5);

insert into dojo_jpa.vendas  values(null, current_timestamp, current_timestamp, 1, 10);
insert into dojo_jpa.vendas  values(null, current_timestamp, current_timestamp, 2, 5);

INSERT INTO dojo_jpa.vendas_itens values (1, 1, 1, current_timestamp, 10);
INSERT INTO dojo_jpa.vendas_itens values (2, 1, 1, current_timestamp, 10);