import java.util.Scanner;
import java.io.PrintWriter;


class WordsJsonMaker{
	static Scanner in = new Scanner(System.in);


	public static void main(String[] args){
		String a;
		String[] b;
		System.out.println("Was ist das Thema Name???");
		String nome = in.nextLine();
		PrintWriter w = null;
		try{
			w = new PrintWriter(nome, "UTF-8");	
		}catch(Exception e){

		}
		
		formataInicio(w, nome);
		while(!(a = in.nextLine()).equals("x")){
			if(a.split(" ").length==2){
				b = lePalavra(a);
				addPalavra(w, b);{}
			}else
				System.out.println("OPS!");	
		}
		formataFinal(w);
		w.close();

	}



	public static void formataInicio(PrintWriter w, String a){
		w.println("{");
		w.println("\"derdiedas\": {");
		w.println("\"+" +a +  "\":[");
	}


	public static void addPalavra(PrintWriter w, String[] p){
		w.println("{");
		w.println("\"a\":\"" + p[0] + "\",");
		w.println("\"p\":\"" + p[1] + "\"");
		w.println("},");
		System.out.println("ok");
	}

	public static void formataFinal(PrintWriter w){
		w.println("]");
		w.println("}");
		w.println("}");
	}




	public static String[] lePalavra(String p){
		String[] a = p.split(" ");
		String[] resp = new String[2];
		if(a[0].equals("Der")|| a[0].equals("der"))
			resp[0] = "0";
		if(a[0].equals("Die")||a[0].equals("die"))
			resp[0] = "1";
		if(a[0].equals("Das")||a[0].equals("das"))
			resp[0] = "2";
		resp[1] = a[1];
		return resp;
	}

}
