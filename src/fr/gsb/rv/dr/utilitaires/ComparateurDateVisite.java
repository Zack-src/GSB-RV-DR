package fr.gsb.rv.dr.utilitaires;

import java.util.Comparator;

import fr.gsb.rv.dr.entites.Praticien;

public class ComparateurDateVisite implements Comparator<Praticien>{

	@Override
	public int compare(Praticien arg0, Praticien arg1) {
		
		if(arg0.getDateDernireVisite().isEqual(arg1.getDateDernireVisite()))
		{
			return 0;
		}
		else if(arg0.getDateDernireVisite().isAfter(arg1.getDateDernireVisite()))
		{
			return 1;			
		}
		else
		{
			return -1;
		}
	}

	
	
}
