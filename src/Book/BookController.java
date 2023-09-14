package Book;

import Database.BookDB;

import java.util.Scanner;

public class BookController implements IBookController {

    private BookDB bookDB = new BookDB();

    public BookController() {
    }

    @Override
    public void showBooks(){
        System.out.println("\n---- Books ----");

        bookDB.showBooks();
    }

    @Override
    public void searchBook(){
        System.out.print("\nSearch by: \n1.Book title.\n2.Author.\nEnter your choice: ");
        Scanner input = new Scanner(System.in);
        Scanner input2 = new Scanner(System.in);

        int choice = input.nextInt();

        switch (choice) {
            case (1):
                System.out.print("Enter book title: ");
                String title = input2.nextLine();

                bookDB.searchByTitle(title);
                break;

            case (2):
                System.out.print("Enter book author: ");
                String author = input2.nextLine();

                bookDB.searchByAuthor(author);
                break;

            default:
                System.out.print("Wrong input!");
        }
    }
}
