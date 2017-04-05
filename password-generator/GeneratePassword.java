package eric;

public class GeneratePassword {
  public static void main(String[] args) {
    // int length, int minNumericLength, int minCharLength, int minSymbolLength
    System.out.println(generatePassword(10, 5, 3, 2));
  }
  public static String generatePassword(int length, int minNumericLength, int minCharLength,
      int minSymbolLength) {
    if (minNumericLength + minCharLength + minSymbolLength > length)
      return "不可能";
    // passwordIndex: 0->type 1->index 2->isUpper[0:lower 1:upper]
    int[][] passwordIndex = new int[length][3];
    // pattern
    char[][] pattern = new char[3][];
    char[] symbol = {'＄', '％', '＠', '＆', '＃', '〄', '＊', 'φ', 'ω', '〃', '♪', '♩', '♭', '∮', '∽', 'ഇ',
        '♬', '♪', '♩'};
    char[] number = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    char[] word = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    pattern[0] = symbol;
    pattern[1] = number;
    pattern[2] = word;

    boolean success = false;
    do {
      // Produce result
      for (int i = 0; i < length; i++) {
        int type = (int) (Math.random() * pattern.length);
        passwordIndex[i][0] = type;
        passwordIndex[i][1] = (int) (Math.random() * pattern[type].length);
        passwordIndex[i][2] = (int) (Math.random() * 2);
      }

      // test case
      int _minNumericLength = 0, _minCharLength = 0, _minSymbolLength = 0;
      for (int i = 0; i < length; i++) {
        if (passwordIndex[i][0] == 1)
          _minNumericLength++;
        if (passwordIndex[i][0] == 2)
          _minCharLength++;
        if (passwordIndex[i][0] == 0)
          _minSymbolLength++;
      }

      if (_minNumericLength >= minNumericLength && _minCharLength >= minCharLength
          && _minSymbolLength >= minSymbolLength)
        success = true;
    } while (!success);

    // Restructuring
    String outString = "";
    for (int i = 0; i < length; i++) {
      String temp = String.valueOf(pattern[passwordIndex[i][0]][passwordIndex[i][1]]);
      outString += (passwordIndex[i][2] == 0) ? temp.toLowerCase() : temp.toUpperCase();
    }
    return outString;
  }
}
