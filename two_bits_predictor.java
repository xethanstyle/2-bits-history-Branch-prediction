package test;

import java.util.Scanner;;

public class two_bits_predictor {
	public static void main(String[] args) {// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);

		int input[] = inputSeri(sc); // input陣列為使用者輸入Taken或Not taken
		if (input[0] != 2) {
			int pre_list[] = new int[input.length]; // pre_list陣列為系統預測Taken或Not taken
			int state[] = stateSeri(sc); // state陣列為使用者輸入4個狀態初始設定
			if (state[0] != 4) {
				System.out.print("您輸入的state初始狀態為: ");
				for (int k = 0; k < state.length; k++) // 列印state
					Sta2string(state[k]);
				System.out.println("\n");
				int history[] = hisSeri(sc);
				if (history[0] != 2) {
					System.out.println();
					System.out.println("                                   系統運作如下");
					System.out.println("------------------------------------------------");
					System.out.println("        history" + "\t" + "          狀態             預測值   輸入值" + "\n");
					for (int x = 0; x < input.length; x++) { // 由輸入Taken or Not taken序列，取得連串2-bit history
						System.out.print("第" + (x + 1) + "個預測:" + "  ");

						if (x == 0) {
							for (int j = 0; j < history.length; j++)
								System.out.print(history[j]);
							System.out.print("\t");
							for (int k = 0; k < state.length; k++)
								Sta2string(state[k]);
							System.out.print("  ");
							pre_list[x] = predict_2(history, state);
							System.out.print("    ");
							input2string(input[x]);
							System.out.print("\n");
							continue;
						} else {
							state[modify_at(history)] = state[modify_at(history)]
									+ modify_sta(pre_list[x - 1], input[x - 1]);

							for (int k = 0; k < state.length; k++) { // 檢查state狀態，不超過邊界值
								if (state[k] > 3)
									state[k] = 3;
								else if (state[k] < 0)
									state[k] = 0;
							}
							history = his_next(history, input[x - 1]); // 更新history
							for (int j = 0; j < history.length; j++) // 列印history
								System.out.print(history[j]);
							System.out.print("\t");
							for (int k = 0; k < state.length; k++) // 列印state
								Sta2string(state[k]);
							System.out.print("  ");
							pre_list[x] = predict_2(history, state); // 將每次的預測值寫入陣列
							System.out.print("    ");
							input2string(input[x]); // 列印input
							System.out.print("\n");
						}

					}

					System.out.println("------------------------------------------------");
					misprediction(pre_list, input);
				} else
					System.out.println("請重新執行程式!!");

			} else
				System.out.println("請重新執行程式!!");
		} else
			System.out.println("請重新執行程式!!");

	}

	

	// 以下為函式區段

	public static int[] inputSeri(Scanner scan) { // 取得使用者輸入T或N，並儲存至input陣列
		System.out.println("請輸入T、N序列:");
		String s = scan.next().toUpperCase();
		System.out.println("您輸入的字串為:" + s + "\n");
		int[] input = new int[s.length()];
		for (int j = 0; j < s.length(); j++) { // 設定
			if ((s.charAt(j) == 'T'))
				input[j] = 1;
			else if ((s.charAt(j) == 'N'))
				input[j] = 0;
			else {
				System.out.println("您輸入字串錯誤!!");
				input[0] = 2;
				break;
			}
		}
		return input;
	}

	public static int[] stateSeri(Scanner scan) { // 取得使用者輸入state初始狀態，並儲存至state陣列
		System.out.println("請輸入4個初始狀態: (提示SN請輸入0、WN請輸入1、WT請輸入2、ST請輸入3)");
		int[] input = new int[4];
		for (int i = 0; i < 4; i++) {
			System.out.print("請輸入第" + (i + 1) + "個數字為:  ");
			int s = scan.nextInt();
			if (s < 4)
				input[i] = s;
			else {
				System.out.println("您輸入範圍錯誤!!");
				input[0] = 4;
				break;
			}
		}
		return input;
	}

	public static int[] hisSeri(Scanner scan) { // 取得使用者輸入history初始狀態，並儲存至history陣列
		System.out.println("請輸入history個初始狀態: (提示00請輸入0、01請輸入1、10請輸入2、11請輸入3)");
		int[] input = new int[2];
		System.out.print("請輸入0~3:");
		int s = scan.nextInt();
		switch (s) {
		case 0:
			input[0] = 0;
			input[1] = 0;
			System.out.println("history初始值為00");
			break;
		case 1:
			input[0] = 0;
			input[1] = 1;
			System.out.println("history初始值為01");
			break;
		case 2:
			input[0] = 1;
			input[1] = 0;
			System.out.println("history初始值為10");
			break;
		case 3:
			input[0] = 1;
			input[1] = 1;
			System.out.println("history初始值為11");
			break;
		default:
			input[0] = 2;
			System.out.println("您輸入範圍錯誤!!");
		}

		return input;
	}

	public static int[] his_next(int u[], int k) { // 計算2-bit history
		u[0] = u[1];
		u[1] = k;
		return u;
	}

	public static void Sta2string(int s) { // 轉換state成文字
		switch (s) {
		case 0:
			System.out.print("SN" + "  ");
			break;
		case 1:
			System.out.print("WN" + "  ");
			break;
		case 2:
			System.out.print("WT" + "  ");
			break;
		case 3:
			System.out.print("ST" + "  ");
			break;
		}
	}

	public static int predict_1(int his[], int s[]) { // 計算預測值為T或N
		int p;
		int pre = 0;
		String str = Integer.toString(his[0]) + Integer.toString(his[1]);
		if (str.equals("00"))
			p = 0;
		else if (str.equals("01"))
			p = 1;
		else if (str.equals("10"))
			p = 2;
		else
			p = 3;
		if (s[p] == 0 || s[p] == 1) {
			pre = 0;
		} else if (s[p] == 2 || s[p] == 3) {
			pre = 1;
		}
		return pre;
	}

	public static int predict_2(int his[], int s[]) { // 計算預測值為T或N，並列印
		int p;
		int pre = 0;
		String str = Integer.toString(his[0]) + Integer.toString(his[1]);
		if (str.equals("00"))
			p = 0;
		else if (str.equals("01"))
			p = 1;
		else if (str.equals("10"))
			p = 2;
		else
			p = 3;
		if (s[p] == 0 || s[p] == 1) {
			pre = 0;
			System.out.print("N" + "  ");
		} else if (s[p] == 2 || s[p] == 3) {
			pre = 1;
			System.out.print("T" + "  ");
		}
		return pre;
	}

	public static void input2string(int s) { // 轉換input成文字
		switch (s) {
		case 0:
			System.out.print("N");
			break;
		case 1:
			System.out.print("T");
			break;
		}
	}

	public static int modify_at(int his[]) { // 修正state位置
		int at;
		String str = Integer.toString(his[0]) + Integer.toString(his[1]);
		if (str.equals("00"))
			at = 0;
		else if (str.equals("01"))
			at = 1;
		else if (str.equals("10"))
			at = 2;
		else
			at = 3;
		return at;
	}

	public static int modify_sta(int pre, int inp) { // 修正state的值
		int modi;
		if (inp == 1)
			modi = 1;
		else if (inp == 0)
			modi = -1;
		else
			modi = 0;
		return modi;
	}

	public static void misprediction(int pre[], int inp[]) { // 計算錯誤機率
		float count = 0;
		float mis = 0;
		for (int i = 0; i < inp.length; i++) {
			if (pre[i] != inp[i])
				count++;
		}
		mis = (float) (count / inp.length);
		System.out.print("輸入" + inp.length + "項 ,預測錯誤" + (int) (count) + "次,錯誤機率" + (mis * 100) + "%");
	}

}
