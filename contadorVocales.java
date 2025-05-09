package guis;

public class contador {

	
	public static void contar_vocales(String a) {
		
		int contA=0;
		int contE=0;
		int contI=0;
		int contO=0;
		int contU=0;
		int largo=a.length();
		a = a.toLowerCase();
		char[] caracteres = a.toCharArray();
		
		for (int i=0;i<largo;i++) {
			
			if(caracteres[i]=='a') {
				contA++;
			}
			else if(caracteres[i]=='e') {
				contE++;
			}
			else if(caracteres[i]=='i') {
				contI++;
			}
			else if(caracteres[i]=='o') {
				contO++;
			}
			else if(caracteres[i]=='u') {
				contU++;
			}
			
				
		
		}
		
		System.out.println(a+" largo: " + largo);
		//System.out.println(caracteres[i]);
		System.out.println("cantidad de A :"  +contA);
		System.out.println("cantidad de E :"  +contE);
		System.out.println("cantidad de I :"  +contI);
		System.out.println("cantidad de O :"  +contO);
		System.out.println("cantidad de U :"  +contU);
		
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String a="AMARILLO";
		
		contar_vocales(a);
		
		

	}

}
