//package tests.fieldsVisibility;

import annotations.Persistent;

public class Main {

	//Method to check if variables are doing their job
	public static void assertCorrection(int result, String var_name) {
		if(result!=LOOPS*INC)
			throw new RuntimeException("This is not the result you are looking for! (A.K.A. plugin not working properly)");
		System.out.printf("Result from var '%s' is correct!\n",var_name);
	}

	public static int LOOPS = 3 ;
	public static int INC = 7 ;

	public int kel_method_not_static() {
		@Persistent int var = 0 ;
		var+=INC;
		System.out.println("var_not_static:"+var);
		return var ;
	}

	Main() {
		int result_not_static = 0 ;
		for(int i=0; i<LOOPS ; i++)
			result_not_static = kel_method_not_static();

		assertCorrection(result_not_static,"result_not_static");
	}

	public static int kel_method() {
		@Persistent int var = 0 ;
		var+=INC;
		System.out.println("var_static:"+var);
		return var ;
	}

	public static void main(String args[]) {
		int result_static = 0 ;
		for(int i=0; i<LOOPS ; i++)
			result_static = kel_method();
		assertCorrection(result_static,"result_static");

		int result_static_inner = 0 ;
		for(int i=0; i<LOOPS ; i++)
			result_static_inner = Inner.meth_inner_static();
		assertCorrection(result_static_inner,"result_static_inner");

		new Main();
		new Inner();

		System.out.println("Plugin working as intended!");
	}

	static class Inner{
		public static int meth_inner_static() {
			@Persistent int var = 0 ;
			var+=INC;
			System.out.println("var_inner_static:"+var);
			return var ;
		}

		Inner() {
			int result_not_static_inner = 0 ;
			for(int i=0; i<LOOPS ; i++)
				result_not_static_inner = meth_inner();
			Main.assertCorrection(result_not_static_inner,"result_not_static_inner");


			int result_not_static_inner_dif_var_name = 0 ;
			for(int i=0; i<LOOPS ; i++)
				result_not_static_inner_dif_var_name = meth_inner_dif_var_name();
			Main.assertCorrection(result_not_static_inner_dif_var_name,"result_not_static_inner_dif_var_name");
		}

		public int meth_inner() {
			@Persistent int var = 0 ;
			var+=INC;
			System.out.println("var_inner:"+var);
			return var ;
		}

		public int meth_inner_dif_var_name() {
			@Persistent int var_random_name = 0 ;
			var_random_name+=INC;
			System.out.println("var_inner_random_name:"+var_random_name);
			return var_random_name ;
		}
	}

}