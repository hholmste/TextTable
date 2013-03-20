class TextTableTest {

    public static void main(String [] args) {
        TextTable table = new TextTable()

        table.headers = ["Many","odd", "things that are", "top right"]
        table.addRow(["this", "is", "sparta", "I think"])

        table.printTo(System.out)
    }

}
