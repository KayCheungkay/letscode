public class A {
    public void foo() {
        java.util.List<String> list = new java.util.ArrayList<>();
        list.forEach(s -> {
            System.out.println(s);
        });
    }

    public static void lambda$foo$0(String a) {

    }

    public static void main(String[] args) {
        new A().foo();
    }
}