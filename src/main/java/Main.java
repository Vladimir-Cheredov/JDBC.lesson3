public class Main {
    public static void main(String[] args) {
        Base usersData = new Base("users");

        usersData.newUsers("VladimirCheredov 28 fear-15@mail.ru");
        usersData.deleteUserByName("null");
        usersData.printAllUsers();
        System.out.println("MyPlusGIGI");
        usersData.printUsersByAge(1,100);
    }
}
