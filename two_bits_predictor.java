package test;

import java.util.Scanner;;

public class two_bits_predictor {
	public static void main(String[] args) {// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);

		int input[] = inputSeri(sc); // input�}�C���ϥΪ̿�JTaken��Not taken
		if (input[0] != 2) {
			int pre_list[] = new int[input.length]; // pre_list�}�C���t�ιw��Taken��Not taken
			int state[] = stateSeri(sc); // state�}�C���ϥΪ̿�J4�Ӫ��A��l�]�w
			if (state[0] != 4) {
				System.out.print("�z��J��state��l���A��: ");
				for (int k = 0; k < state.length; k++) // �C�Lstate
					Sta2string(state[k]);
				System.out.println("\n");
				int history[] = hisSeri(sc);
				if (history[0] != 2) {
					System.out.println();
					System.out.println("                                   �t�ιB�@�p�U");
					System.out.println("------------------------------------------------");
					System.out.println("        history" + "\t" + "          ���A             �w����   ��J��" + "\n");
					for (int x = 0; x < input.length; x++) { // �ѿ�JTaken or Not taken�ǦC�A���o�s��2-bit history
						System.out.print("��" + (x + 1) + "�ӹw��:" + "  ");

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

							for (int k = 0; k < state.length; k++) { // �ˬdstate���A�A���W�L��ɭ�
								if (state[k] > 3)
									state[k] = 3;
								else if (state[k] < 0)
									state[k] = 0;
							}
							history = his_next(history, input[x - 1]); // ��shistory
							for (int j = 0; j < history.length; j++) // �C�Lhistory
								System.out.print(history[j]);
							System.out.print("\t");
							for (int k = 0; k < state.length; k++) // �C�Lstate
								Sta2string(state[k]);
							System.out.print("  ");
							pre_list[x] = predict_2(history, state); // �N�C�����w���ȼg�J�}�C
							System.out.print("    ");
							input2string(input[x]); // �C�Linput
							System.out.print("\n");
						}

					}

					System.out.println("------------------------------------------------");
					misprediction(pre_list, input);
				} else
					System.out.println("�Э��s����{��!!");

			} else
				System.out.println("�Э��s����{��!!");
		} else
			System.out.println("�Э��s����{��!!");

	}

	

	// �H�U���禡�Ϭq

	public static int[] inputSeri(Scanner scan) { // ���o�ϥΪ̿�JT��N�A���x�s��input�}�C
		System.out.println("�п�JT�BN�ǦC:");
		String s = scan.next().toUpperCase();
		System.out.println("�z��J���r�ꬰ:" + s + "\n");
		int[] input = new int[s.length()];
		for (int j = 0; j < s.length(); j++) { // �]�w
			if ((s.charAt(j) == 'T'))
				input[j] = 1;
			else if ((s.charAt(j) == 'N'))
				input[j] = 0;
			else {
				System.out.println("�z��J�r����~!!");
				input[0] = 2;
				break;
			}
		}
		return input;
	}

	public static int[] stateSeri(Scanner scan) { // ���o�ϥΪ̿�Jstate��l���A�A���x�s��state�}�C
		System.out.println("�п�J4�Ӫ�l���A: (����SN�п�J0�BWN�п�J1�BWT�п�J2�BST�п�J3)");
		int[] input = new int[4];
		for (int i = 0; i < 4; i++) {
			System.out.print("�п�J��" + (i + 1) + "�ӼƦr��:  ");
			int s = scan.nextInt();
			if (s < 4)
				input[i] = s;
			else {
				System.out.println("�z��J�d����~!!");
				input[0] = 4;
				break;
			}
		}
		return input;
	}

	public static int[] hisSeri(Scanner scan) { // ���o�ϥΪ̿�Jhistory��l���A�A���x�s��history�}�C
		System.out.println("�п�Jhistory�Ӫ�l���A: (����00�п�J0�B01�п�J1�B10�п�J2�B11�п�J3)");
		int[] input = new int[2];
		System.out.print("�п�J0~3:");
		int s = scan.nextInt();
		switch (s) {
		case 0:
			input[0] = 0;
			input[1] = 0;
			System.out.println("history��l�Ȭ�00");
			break;
		case 1:
			input[0] = 0;
			input[1] = 1;
			System.out.println("history��l�Ȭ�01");
			break;
		case 2:
			input[0] = 1;
			input[1] = 0;
			System.out.println("history��l�Ȭ�10");
			break;
		case 3:
			input[0] = 1;
			input[1] = 1;
			System.out.println("history��l�Ȭ�11");
			break;
		default:
			input[0] = 2;
			System.out.println("�z��J�d����~!!");
		}

		return input;
	}

	public static int[] his_next(int u[], int k) { // �p��2-bit history
		u[0] = u[1];
		u[1] = k;
		return u;
	}

	public static void Sta2string(int s) { // �ഫstate����r
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

	public static int predict_1(int his[], int s[]) { // �p��w���Ȭ�T��N
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

	public static int predict_2(int his[], int s[]) { // �p��w���Ȭ�T��N�A�æC�L
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

	public static void input2string(int s) { // �ഫinput����r
		switch (s) {
		case 0:
			System.out.print("N");
			break;
		case 1:
			System.out.print("T");
			break;
		}
	}

	public static int modify_at(int his[]) { // �ץ�state��m
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

	public static int modify_sta(int pre, int inp) { // �ץ�state����
		int modi;
		if (inp == 1)
			modi = 1;
		else if (inp == 0)
			modi = -1;
		else
			modi = 0;
		return modi;
	}

	public static void misprediction(int pre[], int inp[]) { // �p����~���v
		float count = 0;
		float mis = 0;
		for (int i = 0; i < inp.length; i++) {
			if (pre[i] != inp[i])
				count++;
		}
		mis = (float) (count / inp.length);
		System.out.print("��J" + inp.length + "�� ,�w�����~" + (int) (count) + "��,���~���v" + (mis * 100) + "%");
	}

}
