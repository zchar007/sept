package com.sept.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;

import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;

import com.sept.exception.AppException;

public final class StringUtil {
	/**
	 * ˵������һ���ַ���trim�������null������null
	 * 
	 * @param s
	 * @return
	 */
	public final static String trim(String s) {
		if (s == null) {
			return null;
		}
		s = s.trim();
		return s;
	}

	public final static String trimAll(String s) {
		if (s == null) {
			return null;
		}
		s = s.replaceAll(" ", "");
		s = s.replaceAll("��", "");
		s = s.replaceAll("	", "");
		return s;
	}

	/**
	 * �ж�����ַ���a is null������b,������a
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public final static String nvlStr(String a, String b) {
		if (a == null) {
			return b;
		}
		return a;
	}

	/**
	 * ȥ���ҿո�
	 * 
	 * @todo:�㷨���Ľ�
	 * @param String
	 *            str
	 * @return String
	 */
	public final static String rTrim(String str) {
		for (int i = str.length() - 1; i > 0; i--) {
			if (str.charAt(i) != ' ') {
				return str;
			}
			str = str.substring(0, i);
		}
		return str.trim();
	}

	/**
	 * ȥ����ո�
	 * 
	 * @param String
	 *            str
	 * @return String
	 */
	public final static String lTrim(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != ' ') {
				return str;
			}
			str = str.substring(i + 1, str.length());
		}
		return str.trim();
	}

	/**
	 * ���ַ���ת��Ϊint������ת�����쳣��
	 * 
	 * @throws AppException
	 */
	public final static int stringToInt(String intString) throws AppException {
		int iRet = 0;

		if (intString == null || intString.equalsIgnoreCase("")) {
			throw new AppException("�������Ϊ��!");
		}

		intString = intString.trim();

		try {
			iRet = Integer.valueOf(intString);
		} catch (NumberFormatException e) {

			try {
				iRet = (int) Double.valueOf(intString).doubleValue();
			} catch (NumberFormatException e1) {
				throw new AppException("StringUtil.stringToDouble����������ַ���["
						+ intString + "]���ܱ�ת��Ϊ����!");
			}

		}

		return iRet;
	}

	/**
	 * ���ַ���ת��Ϊint������ת�����쳣��
	 * 
	 * @throws AppException
	 */
	public final static double stringToDouble(String s) throws AppException {
		double i = 0;
		if (s == null || s.equalsIgnoreCase("")) {
			throw new AppException("�������Ϊ��!");
		}
		s = s.trim();
		try {
			DecimalFormat df = new DecimalFormat("");
			i = df.parse(s).doubleValue();
		} catch (ParseException e) {
			throw new AppException("StringUtil.stringToDouble����������ַ���[" + s
					+ "]����һ���������ֵ��ַ���!");
		}
		return i;

	}

	/**
	 * ���ַ���ת��Ϊƴ������ĸ��
	 * 
	 * @param String
	 *            str
	 * @return String
	 * @throws AppException
	 */
	public final static String getPy(String str) throws AppException {
		return str;
		// return GetPy.getGBKpy(str);
	}

	/**
	 * ���ܼ򵥵�ʹ�ã�new String(string.getBytes("gbk"),"utf-8");
	 * 
	 * @author zqr
	 * @date ����ʱ�� Jul 8, 2010
	 * @since V3.9
	 */
	public final static byte[] gbk2utf8(String chenese) {
		char c[] = chenese.toCharArray();
		byte[] fullByte = new byte[3 * c.length];
		for (int i = 0; i < c.length; i++) {
			int m = (int) c[i];
			String word = Integer.toBinaryString(m);

			StringBuffer sb = new StringBuffer();
			int len = 16 - word.length();
			// ����
			for (int j = 0; j < len; j++) {
				sb.append("0");
			}
			sb.append(word);
			sb.insert(0, "1110");
			sb.insert(8, "10");
			sb.insert(16, "10");

			String s1 = sb.substring(0, 8);
			String s2 = sb.substring(8, 16);
			String s3 = sb.substring(16);

			byte b0 = Integer.valueOf(s1, 2).byteValue();
			byte b1 = Integer.valueOf(s2, 2).byteValue();
			byte b2 = Integer.valueOf(s3, 2).byteValue();
			byte[] bf = new byte[3];
			bf[0] = b0;
			fullByte[i * 3] = bf[0];
			bf[1] = b1;
			fullByte[i * 3 + 1] = bf[1];
			bf[2] = b2;
			fullByte[i * 3 + 2] = bf[2];

		}
		return fullByte;
	}

	/**
	 * Description:String��length()���������ַ������ȣ�Ӣ��һ����ĸ��һ��������һ����Ҳ��һ����
	 * <p>
	 * �������ʱ���ܾ�ȷȷ����ʾ���ݵĳ��ȡ���дchnSubstring��chnStrLen�����������ַ�
	 * <p>
	 * �����Ⱥͽ�ȡ�Ӵ�ʱ��һ�����ֳ�����������
	 * <p>
	 * ������chnSubstring������
	 * 
	 * @param chinaString
	 * @param start
	 * @param end
	 * @return
	 * @throws AppException
	 */
	public final static String chnSubstring(String chinaString, int start, int end)
			throws AppException {
		if (null == chinaString)
			return null;
		int startIdx = 0, endIdx = chinaString.length();
		int currentPos = 0, currentCharLen = 0;
		boolean flag = false;

		if (end > chnStrLen(chinaString)) {
			end = chnStrLen(chinaString);
		}
		if (start >= end)
			return "";

		for (int i = 0; i < chinaString.length(); i++) {
			if ((int) chinaString.charAt(i) > 255)
				currentCharLen = 2;
			else
				currentCharLen = 1;

			if (flag == false && currentPos >= start) {
				if (currentPos > start)
					startIdx = i - 1;
				else
					startIdx = i;
				flag = true;
			}
			if (flag == true && currentPos + currentCharLen > end) {
				endIdx = i;
				break;
			}
			currentPos += currentCharLen;
		}
		if (flag == false) {
			startIdx = chinaString.length() - 1;
		}
		return chinaString.substring(startIdx, endIdx);
	}

	/**
	 * Description:String��length()���������ַ������ȣ�Ӣ��һ����ĸ��һ��������һ����Ҳ��һ����
	 * <p>
	 * �������ʱ���ܾ�ȷȷ����ʾ���ݵĳ��ȡ���дchnSubstring��chnStrLen�����������ַ�
	 * <p>
	 * �����Ⱥͽ�ȡ�Ӵ�ʱ��һ�����ֳ�����������
	 * <p>
	 * ������chnStrLen������
	 * 
	 * @param chinaString
	 * @param start
	 * @param end
	 * @return
	 */
	public final static int chnStrLen(String chinaString) {
		int len = 0;
		for (int i = 0; i < chinaString.length(); i++) {
			if (((int) chinaString.charAt(i)) > 255)
				len += 2;
			else
				len++;
		}
		return len;
	}

	/**
	 * ��ȡ�ַ����к��ֵĸ���
	 * 
	 * @param chinaString
	 * @return
	 */
	public final static int getChnNumber(String chinaString) {
		int len = 0;
		for (int i = 0; i < chinaString.length(); i++) {
			if (((int) chinaString.charAt(i)) > 255)
				len++;
		}
		return len;
	}

	/**
	 * ��ȡ�ַ�������ĸ�����ֵĸ���
	 * 
	 * @param chinaString
	 * @return
	 */
	public final static int getchrNumber(String chinaString) {
		int len = 0;
		for (int i = 0; i < chinaString.length(); i++) {
			if (((int) chinaString.charAt(i)) > 255)
				continue;
			else
				len++;
		}
		return len;
	}

	/**
	 * ��ȡ�ַ����ĳ���
	 * 
	 * @param value
	 *            ԭʼ�ַ���
	 * @param chinaLen
	 *            ÿ��������ռ�Ŀ��
	 * @param englishLen
	 *            ÿ���Ǻ�����ռ�Ŀ��
	 * @return �����ַ�����ռ���ܿ��
	 */
	public final static int getCssLength(String value, int chinaLen,
			int englishLen) {
		int clen = 0;
		int elen = 0;
		for (int i = 0, j = value.length(); i < j; i++) {
			if ((int) value.charAt(i) > 255) {
				clen += chinaLen;
			} else {
				elen += englishLen;
			}
		}
		return clen + elen;
	}

	/**
	 * ˵���������֤�еõ�����
	 * 
	 * @author:֣���� May 13, 2009
	 * @param card
	 * @return
	 * @throws AppException
	 */
	public final static int getAgeFromCard(String card) throws AppException {
		int age = -1;
		int length = card.length();
		String birthday = "";
		// 20120929 modi by www �޸ĸ������֤��������Ļ���
		if (length == 15) {
			birthday = card.substring(6, 8);
			birthday = "19" + birthday;
		} else if (length == 18)
			birthday = card.substring(6, 14);
		else
			throw new AppException("��������֤��!");
		// ��ȡ��ǰʱ��
		Date currentTime = new Date();
		// �������ǰʱ����������µ�������
		int diffMonths = DateUtil.getMonthDifferenceBetweenTwoDate(
				DateUtil.formatStrToDate(birthday), currentTime);

		// Ԥ�����֤��Ϣ�д�
		if (diffMonths < 0) {
			throw new AppException("��������֤�ţ��������ڴ��ڵ�ǰ����!");
		}

		// �������䣬����12���º���
		age = diffMonths / 12;
		// 20120929 end modi by www �޸ĸ������֤��������Ļ���
		return age;
	}

	/**
	 * �ж����֤�����Ƿ�Ϸ�
	 */
	public final static String validateCard(String p_sfzhm) throws AppException {
		int vsfz_sum, vsfz_vi, vsfz_si, vsfz_wi, vsfz_vj, vsfz_res;
		String vsfz_temp, vsfz_str, vsfz_a, vsfz_jg;
		String vsfz_err = "0";

		if (p_sfzhm == null) {
			throw new AppException("��������֤����Ϊ��!");
		}
		p_sfzhm = p_sfzhm.trim();
		if (p_sfzhm.length() == 18) {
			vsfz_temp = "10X98765432";
			vsfz_sum = 0;
			vsfz_vi = 0;
			vsfz_str = p_sfzhm.substring(0, 17);
			while ("0".equals(vsfz_err) && vsfz_vi <= 16) {
				vsfz_a = vsfz_str.substring(vsfz_vi, vsfz_vi + 1);
				if ("9".compareTo(vsfz_a) < 0 || "0".compareTo(vsfz_a) > 0) {
					vsfz_err = "1";
				}
				vsfz_vi++;
			}
			if ("1".equals(vsfz_err)) {
				return "���֤����ĵ�" + vsfz_vi + "λ�ǷǷ��������޸�!";
			}

			boolean vflag = DateUtil.isDate(p_sfzhm.substring(6, 14));
			if (!vflag) {
				return "���֤����ĳ������ڲ��ԣ����޸�!";
			}

			vsfz_vi = 0;
			while (vsfz_vi <= 16) {
				vsfz_a = p_sfzhm.substring(vsfz_vi, vsfz_vi + 1);
				vsfz_si = stringToInt(vsfz_a);
				vsfz_vj = 0;
				vsfz_wi = 1;
				while (vsfz_vj < 17 - vsfz_vi) {
					vsfz_wi = vsfz_wi * 2;
					vsfz_vj = vsfz_vj + 1;
				}
				vsfz_sum = vsfz_sum + vsfz_si * vsfz_wi;
				vsfz_vi++;
			}
			vsfz_res = vsfz_sum % 11;
			vsfz_jg = vsfz_temp.substring(vsfz_res, vsfz_res + 1);
			if (!vsfz_jg.equals(p_sfzhm.substring(17, 18))) {
				// return "���֤�������һλ���ԣ�Ӧ����" + vsfz_jg+"!";
				return "���֤�����У��λ���ԣ�����!";
			}
		} else {
			return "���֤����ĳ��Ȳ���18λ!";
		}
		return "";
	}

	/**
	 * ���ܣ����ַ���������𣬸������Ƴ��Ƚ�ȡ�ַ������ַ����а������֣�һ�����ֵ��������ַ��� ������һ���ַ��ǰ�����������ȥ��
	 * 
	 * @param strParameter
	 *            Ҫ��ȡ���ַ���
	 * @param limitLength
	 *            ��ȡ�ĳ���
	 * @return ��ȡ����ַ���
	 * @throws AppException
	 */
	public final static String leftb(String strParameter, int limitLength)
			throws AppException {
		if (strParameter == null) {
			throw new AppException("������ַ�������Ϊ��!");
		}
		String return_str = strParameter; // ���ص��ַ���
		int temp_int = 0; // ������ת���������ַ�����ַ�������
		int cut_int = 0; // ��ԭʼ�ַ�����ȡ�ĳ���
		byte[] b = strParameter.getBytes(); // ���ַ���ת�����ַ�����
		int last_length;
		for (int i = 0; i < b.length; i++) {
			if (b[i] >= 0) {
				last_length = 1; // Ӣ���ַ�
			} else {
				last_length = 2; // һ�����ֵ��������ַ�
				i++;
			}
			temp_int = temp_int + last_length;
			cut_int++;
			if (temp_int >= limitLength) {
				if (temp_int > limitLength) {
					temp_int -= last_length;
					cut_int--;
				}
				return_str = return_str.substring(0, cut_int);
				break;
			}
		}
		return return_str;
	}

	/**
	 * ����
	 * 
	 * @param originalString
	 * @param encoding
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public final static String decode(String originalString, String encoding)
			throws UnsupportedEncodingException {
		return java.net.URLDecoder.decode(originalString, encoding);
	}

	public final static String encode(String originalString, String encoding)
			throws UnsupportedEncodingException {
		return java.net.URLEncoder.encode(originalString, encoding);
	}

	public final static String getUUID() {
		UUIDGenerator generator = UUIDGenerator.getInstance();
		UUID uuid = generator.generateRandomBasedUUID();
		String randomString = uuid.toString();
		randomString = randomString.replaceAll("-", "_");
		return randomString;
	}

	/**
	 * ��ȡһ����ĸ�����ִ�Сд���������ֵ�ASCIIֵ�������ַ��ķ���"-1"
	 * 
	 */
	public final static String getASCIIOfLetterAndNumber(String str) {
		char a = str.charAt(0);
		int b = a;

		// ���ֵķ�Χ��48-57����д�ַ�����65-90��Сд��ĸ����97-122
		if ((47 < b && b < 58) || (64 < b && b < 91) || (96 < b && b < 123)) {
			return String.valueOf(b);
		} else {
			return "-1";
		}
	}

	public final static void main(String[] args) throws AppException {
		System.out.println(trim("ds	ni ��skasdsa"));
	}
}
