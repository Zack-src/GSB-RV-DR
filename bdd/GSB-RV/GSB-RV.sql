drop database if exists gsbrv;
create database gsbrv;

use gsbrv;


CREATE TABLE `Motif` (
    `mot_num` int(20) not null auto_increment,
    `mot_libelle` varchar(255),
    PRIMARY KEY (`mot_num`)
);


CREATE TABLE `ActiviteCompl` (
  `ac_num` int(11) NOT NULL DEFAULT '0',
  `ac_date` date DEFAULT NULL,
  `ac_lieu` varchar(50) DEFAULT NULL,
  `ac_theme` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ac_num`)
);


CREATE TABLE `Composant` (
  `cmp_code` varchar(8) NOT NULL DEFAULT '',
  `cmp_libelle` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`cmp_code`)
);


CREATE TABLE `Famille` (
  `fam_code` varchar(6) NOT NULL DEFAULT '',
  `fam_libelle` varchar(160) DEFAULT NULL,
  PRIMARY KEY (`fam_code`)
);


CREATE TABLE `Medicament` (
  `med_depotlegal` varchar(20) NOT NULL DEFAULT '',
  `med_nomcommercial` varchar(50) DEFAULT NULL,
  `fam_code` varchar(6) DEFAULT NULL,
  `med_composition` varchar(510) DEFAULT NULL,
  `med_effets` varchar(510) DEFAULT NULL,
  `med_contreindic` varchar(510) DEFAULT NULL,
  `med_prixechantillon` float DEFAULT NULL,
  PRIMARY KEY (`med_depotlegal`),
  KEY `FK_MEDICAMENT_FAMILLE` (`fam_code`),
  CONSTRAINT `FK_MEDICAMENT_FAMILLE` FOREIGN KEY (`fam_code`) REFERENCES `Famille` (`fam_code`)
);


CREATE TABLE `Constituer` (
  `med_depotlegal` varchar(20) NOT NULL DEFAULT '',
  `cmp_code` varchar(8) NOT NULL DEFAULT '',
  `cst_qte` float DEFAULT NULL,
  PRIMARY KEY (`med_depotlegal`,`cmp_code`),
  KEY `FK_CONSTITUER_COMPOSANT` (`cmp_code`),
  CONSTRAINT `FK_CONSTITUER_COMPOSANT` FOREIGN KEY (`cmp_code`) REFERENCES `Composant` (`cmp_code`),
  CONSTRAINT `FK_CONSTITUER_MEDICAMENT` FOREIGN KEY (`med_depotlegal`) REFERENCES `Medicament` (`med_depotlegal`)
);


CREATE TABLE `Dosage` (
  `dos_code` varchar(20) NOT NULL DEFAULT '',
  `dos_quantite` varchar(20) DEFAULT NULL,
  `dos_unite` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`dos_code`)
);


CREATE TABLE `Presentation` (
  `pre_code` varchar(4) NOT NULL DEFAULT '',
  `pre_libelle` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`pre_code`)
);


CREATE TABLE `Formuler` (
  `med_depotlegaL` varchar(20) NOT NULL DEFAULT '',
  `pre_code` varchar(4) NOT NULL DEFAULT '',
  PRIMARY KEY (`med_depotlegaL`,`pre_code`),
  KEY `FK_FORMULER_PRESENTATION` (`pre_code`),
  CONSTRAINT `FK_FORMULER_MEDICAMENT` FOREIGN KEY (`med_depotlegal`) REFERENCES `Medicament` (`med_depotlegal`),
  CONSTRAINT `FK_FORMULER_PRESENTATION` FOREIGN KEY (`pre_code`) REFERENCES `Presentation` (`pre_code`)
);


CREATE TABLE `Interagir` (
  `med_perturbateur` varchar(20) NOT NULL DEFAULT '',
  `med_perturbe` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`med_perturbateur`,`med_perturbe`),
  KEY `FK_INTERAGIR_PERTURBE_MEDICAMENT` (`med_perturbe`),
  CONSTRAINT `FK_INTERAGIR_PERTURBATEUR_MEDICAMENT` FOREIGN KEY (`med_perturbateur`) REFERENCES `Medicament` (`med_depotlegal`),
  CONSTRAINT `FK_INTERAGIR_PERTURBE_MEDICAMENT` FOREIGN KEY (`med_perturbe`) REFERENCES `Medicament` (`med_depotlegal`)
);


CREATE TABLE `TypePraticien` (
  `typ_code` varchar(6) NOT NULL DEFAULT '',
  `typ_libelle` varchar(50) DEFAULT NULL,
  `typ_lieu` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`typ_code`)
);


CREATE TABLE `Praticien` (
  `pra_num` int(11) NOT NULL DEFAULT '0',
  `pra_nom` varchar(50) DEFAULT NULL,
  `pra_prenom` varchar(60) DEFAULT NULL,
  `pra_adresse` varchar(100) DEFAULT NULL,
  `pra_cp` varchar(10) DEFAULT NULL,
  `pra_ville` varchar(50) DEFAULT NULL,
  `pra_coefnotoriete` float DEFAULT NULL,
  `typ_code` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`pra_num`),
  KEY `FK_PRATICIEN_TYPE_PRATICIEN` (`typ_code`),
  CONSTRAINT `FK_PRATICIEN_TYPE_PRATICIEN` FOREIGN KEY (`typ_code`) REFERENCES `TypePraticien` (`typ_code`)
);


CREATE TABLE `Inviter` (
  `ac_num` int(11) NOT NULL DEFAULT '0',
  `pra_num` int(11) NOT NULL DEFAULT '0',
  `specialisation` char(1) DEFAULT NULL,
  PRIMARY KEY (`ac_num`,`pra_num`),
  KEY `FK_INVITER_PRATICIEN` (`pra_num`),
  CONSTRAINT `FK_INVITER_ACTIVITE_COMPL` FOREIGN KEY (`ac_num`) REFERENCES `ActiviteCompl` (`ac_num`),
  CONSTRAINT `FK_INVITER_PRATICIEN` FOREIGN KEY (`pra_num`) REFERENCES `Praticien` (`pra_num`)
);


CREATE TABLE `Laboratoire` (
  `lab_code` varchar(4) NOT NULL DEFAULT '',
  `lab_nom` varchar(20) DEFAULT NULL,
  `lab_chefvente` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`lab_code`)
);


CREATE TABLE `Secteur` (
  `sec_code` varchar(2) NOT NULL DEFAULT '',
  `sec_libelle` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`sec_code`)
);


CREATE TABLE `Visiteur` (
  `vis_matricule` varchar(20) NOT NULL DEFAULT '',
  `vis_nom` varchar(50) DEFAULT NULL,
  `vis_prenom` varchar(100) DEFAULT NULL,
  `vis_adresse` varchar(100) DEFAULT NULL,
  `vis_cp` varchar(10) DEFAULT NULL,
  `vis_ville` varchar(60) DEFAULT NULL,
  `vis_dateembauche` date DEFAULT NULL,
  `sec_code` varchar(2) DEFAULT NULL,
  `lab_code` varchar(4) DEFAULT NULL,
  `vis_mdp` varchar(30) DEFAULT 'azerty',
  PRIMARY KEY (`vis_matricule`),
  KEY `FK_VISITEUR_SECTEUR` (`sec_code`),
  KEY `FK_VISITEUR_LABORATOIRE` (`lab_code`),
  CONSTRAINT `FK_VISITEUR_LABORATOIRE` FOREIGN KEY (`lab_code`) REFERENCES `Laboratoire` (`lab_code`),
  CONSTRAINT `FK_VISITEUR_SECTEUR` FOREIGN KEY (`sec_code`) REFERENCES `Secteur` (`sec_code`)
);


CREATE TABLE `RapportVisite` (
  `vis_matricule` varchar(20) NOT NULL DEFAULT '',
  `rap_num` int(11) NOT NULL,
  `rap_date_visite` date NOT NULL,
  `rap_bilan` varchar(510) DEFAULT '',
  `pra_num` int(11) DEFAULT NULL,
  `mot_num` int(20) DEFAULT NULL,
  `rap_autre_motif` varchar(30) DEFAULT '',
  `rap_coef_confiance` int(2) DEFAULT NULL,
  `rap_autre_modif` date DEFAULT NULL,
  PRIMARY KEY (`vis_matricule`,`rap_num`),
  KEY `FK_RAPPORT_VISITE_PRATICIEN` (`pra_num`),
  CONSTRAINT `FK_RAPPORT_VISITE_PRATICIEN` FOREIGN KEY (`pra_num`) REFERENCES `Praticien` (`pra_num`),
  CONSTRAINT `FK_RAPPORT_VISITE_VISITEUR` FOREIGN KEY (`vis_matricule`) REFERENCES `Visiteur` (`vis_matricule`),
  CONSTRAINT `FK_RAPPORT-VISITE_MOTIF` FOREIGN KEY (`mot_num`) REFERENCES `Motif` (`mot_num`)
);


CREATE TABLE `Offrir` (
  `vis_matricule` varchar(20) NOT NULL DEFAULT '',
  `rap_num` int(11) NOT NULL DEFAULT '0',
  `med_depotlegal` varchar(20) NOT NULL DEFAULT '',
  `off_quantite` int(2) NOT NULL DEFAULT '1',
  PRIMARY KEY (`vis_matricule`,`rap_num`,`med_depotlegal`),
  KEY `FK_OFFRIR_MEDICAMENT` (`med_depotlegal`),
  KEY `OFFRIR` (`vis_matricule`, `rap_num`),
  CONSTRAINT `FK_OFFRIR_VISITEUR` FOREIGN KEY (`vis_matricule`,`rap_num`) REFERENCES `RapportVisite` (`vis_matricule`,`rap_num`),
  CONSTRAINT `FK_OFFRIR_MEDICAMENT` FOREIGN KEY (`med_depotlegal`) REFERENCES `Medicament` (`med_depotlegal`)
);

CREATE TABLE `Specialite` (
  `spe_code` varchar(10) NOT NULL DEFAULT '',
  `spe_libelle` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`spe_code`)
);

CREATE TABLE `Posseder` (
  `pra_num` int(11) NOT NULL DEFAULT '0',
  `spe_code` varchar(10) NOT NULL DEFAULT '',
  `pos_diplome` varchar(20) DEFAULT NULL,
  `pos_coefprescription` float DEFAULT NULL,
  PRIMARY KEY (`pra_num`,`spe_code`),
  KEY `FK_POSSEDER_SPECIALITE` (`spe_code`),
  CONSTRAINT `FK_POSSEDER_PRATICIEN` FOREIGN KEY (`pra_num`) REFERENCES `Praticien` (`pra_num`),
  CONSTRAINT `FK_POSSEDER_SPECIALITE` FOREIGN KEY (`spe_code`) REFERENCES `Specialite` (`spe_code`)
);

CREATE TABLE `TypeIndividu` (
  `tin_code` varchar(10) NOT NULL DEFAULT '',
  `tin_libelle` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`tin_code`)
);

CREATE TABLE `Prescrire` (
  `med_depotlegal` varchar(20) NOT NULL DEFAULT '',
  `tin_code` varchar(10) NOT NULL DEFAULT '',
  `dos_code` varchar(20) NOT NULL DEFAULT '',
  `pre_posologie` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`med_depotlegal`,`tin_code`,`dos_code`),
  KEY `FK_PRESCRIRE_TYPE_INDIVIDU` (`tin_code`),
  KEY `FK_PRESCRIRE_DOSAGE` (`dos_code`),
  CONSTRAINT `FK_PRESCRIRE_DOSAGE` FOREIGN KEY (`dos_code`) REFERENCES `Dosage` (`dos_code`),
  CONSTRAINT `FK_PRESCRIRE_MEDICAMENT` FOREIGN KEY (`med_depotlegal`) REFERENCES `Medicament` (`med_depotlegal`),
  CONSTRAINT `FK_PRESCRIRE_TYPE_INDIVIDU` FOREIGN KEY (`tin_code`) REFERENCES `TypeIndividu` (`tin_code`)
);


CREATE TABLE `Realiser` (
  `ac_num` int(11) NOT NULL DEFAULT '0',
  `vis_matricule` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`ac_num`,`vis_matricule`),
  KEY `FK_REALISER_VISITEUR` (`vis_matricule`),
  CONSTRAINT `FK_REALISER_ACTIVITE_COMPL` FOREIGN KEY (`ac_num`) REFERENCES `ActiviteCompl` (`ac_num`),
  CONSTRAINT `FK_REALISER_VISITEUR` FOREIGN KEY (`vis_matricule`) REFERENCES `Visiteur` (`vis_matricule`)
);


CREATE TABLE `Region` (
  `reg_code` varchar(4) NOT NULL DEFAULT '',
  `sec_code` varchar(2) DEFAULT NULL,
  `reg_nom` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`reg_code`),
  KEY `FK_REGION_SECTEUR` (`sec_code`),
  CONSTRAINT `FK_REGION_SECTEUR` FOREIGN KEY (`sec_code`) REFERENCES `Secteur` (`sec_code`)
);


CREATE TABLE `Travailler` (
  `vis_matricule` varchar(20) NOT NULL DEFAULT '',
  `jjmmaa` date NOT NULL,
  `reg_code` varchar(4) NOT NULL DEFAULT '',
  `tra_role` varchar(22) DEFAULT NULL,
  PRIMARY KEY (`vis_matricule`,`jjmmaa`,`reg_code`),
  KEY `FK_TRAVAILLER_REGION` (`reg_code`),
  CONSTRAINT `FK_TRAVAILLER_REGION` FOREIGN KEY (`reg_code`) REFERENCES `Region` (`reg_code`),
  CONSTRAINT `FK_TRAVAILLER_VISITEUR` FOREIGN KEY (`vis_matricule`) REFERENCES `Visiteur` (`vis_matricule`)
);


CREATE TABLE connaitre (
    `rap_num` int(11) not null,
    `vis_matricule` varchar(20) not null,
    `med_depotlegal` varchar(20) not null ,
    `niveau` int(2),
    PRIMARY KEY (`vis_matricule`, `rap_num` ,`med_depotlegal`),
    KEY `FK_CONNAITRE_MEDICAMENT` (`med_depotlegal`),
    KEY `FK_CONNAITRE_VISITEUR_RAPPORT_VISI` (`vis_matricule`,`med_depotlegal`),
    CONSTRAINT `FK_CONNAITRE_VISITEUR` FOREIGN KEY (`vis_matricule` ,`rap_num`) REFERENCES `RapportVisite` (`vis_matricule` ,`rap_num`),
    CONSTRAINT `FK_CONNAITRE_MEDICAMENT` FOREIGN KEY (`med_depotlegal`) REFERENCES `Medicament` (`med_depotlegal`)
);

/*Requete :

SELECT Visiteur.vis_matricule FROM Visiteur INNER JOIN Travailler ON Visiteur.vis_matricule = Travailler.vis_matricule WHERE tra_role LIKE "Délégué";
SELECT Visiteur.vis_matricule FROM Visiteur INNER JOIN Travailler ON Visiteur.vis_matricule = Travailler.vis_matricule WHERE tra_role LIKE "Délégué" AND jjmmaa =  (SELECT max(jjmmaa) FROM Travailler where tra_role = "Délégué" ) ;
SELECT Visiteur.vis_matricule, vis_nom, vis_prenom FROM Visiteur INNER JOIN Travailler ON Visiteur.vis_matricule = Travailler.vis_matricule WHERE tra_role LIKE "Délégué" AND jjmmaa =  (SELECT max(jjmmaa) FROM Travailler where tra_role = "Délégué" ) AND Visiteur.vis_matricule LIKE "p49" and vis_mdp LIKE "azerty"  ;

*/