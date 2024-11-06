package helper;

public class Question {
    // Hàm trả về vị trí cốc chứa bóng (số 1, 2 hoặc 3)
    public static int renQuestion() {
        return randomInt3();
    }

    // Hàm sinh ngẫu nhiên số từ 1 đến 3 (tương ứng với 3 cốc)
    static int randomInt3() {
        return (int) (Math.random() * 3) + 1;
    }

}
