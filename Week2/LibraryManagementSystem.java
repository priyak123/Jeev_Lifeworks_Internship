package week2;

import java.time.LocalDate;
import java.util.*;

class Book {
	private UUID bookID;
	private String title;
	private String author;
	private String genre;
	private boolean isIssued;
	private Member issuedTo;
	private LocalDate dueDate;
	private Queue<Member> reservationQueue = new LinkedList<>();

	public Book(String title, String author, String genre) {
		this.bookID = UUID.randomUUID();
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.isIssued = false;
	}

	public UUID getBookID() {
		return bookID;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getGenre() {
		return genre;
	}

	public boolean isIssued() {
		return isIssued;
	}

	public Member getIssuedTo() {
		return issuedTo;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public Queue<Member> getReservationQueue() {
		return reservationQueue;
	}

	public void setIssued(boolean issued) {
		isIssued = issued;
	}

	public void setIssuedTo(Member issuedTo) {
		this.issuedTo = issuedTo;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
}

abstract class Member {
	private UUID memberID;
	private String name;
	private String email;
	private String phone;
	private int maxBooksAllowed;
	private List<Book> currentlyIssuedBooks = new ArrayList<>();

	public Member(String name, String email, String phone, int maxBooksAllowed) {
		this.memberID = UUID.randomUUID();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.maxBooksAllowed = maxBooksAllowed;
	}

	public UUID getMemberID() {
		return memberID;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public int getMaxBooksAllowed() {
		return maxBooksAllowed;
	}

	public List<Book> getCurrentlyIssuedBooks() {
		return currentlyIssuedBooks;
	}

	public abstract int getMaxAllowedDays();

	public abstract String getMemberType();
}

class StudentMember extends Member {
	public StudentMember(String name, String email, String phone) {
		super(name, email, phone, 3);
	}

	public int getMaxAllowedDays() {
		return 14;
	}

	public String getMemberType() {
		return "Student";
	}
}

class TeacherMember extends Member {
	public TeacherMember(String name, String email, String phone) {
		super(name, email, phone, 5);
	}

	public int getMaxAllowedDays() {
		return 30;
	}

	public String getMemberType() {
		return "Teacher";
	}
}

class GuestMember extends Member {
	public GuestMember(String name, String email, String phone) {
		super(name, email, phone, 1);
	}

	public int getMaxAllowedDays() {
		return 7;
	}

	public String getMemberType() {
		return "Guest";
	}
}

class Librarian extends Member {
	private List<Book> catalog = new ArrayList<>();
	private List<Member> members = new ArrayList<>();

	public Librarian(String name, String email, String phone) {
		super(name, email, phone, 10);
	}

	public int getMaxAllowedDays() {
		return 30;
	}

	public String getMemberType() {
		return "Librarian";
	}

	public void registerMember(Member member) {
		for (Member m : members) {
			if (m.getEmail().equals(member.getEmail()) || m.getPhone().equals(member.getPhone())) {
				System.out.println("Member already exists.");
				return;
			}
		}
		members.add(member);
		System.out.println("Member registered successfully.");
	}

	public void addBook(Book book) {
		for (Book b : catalog) {
			if (b.getBookID().equals(book.getBookID())) {
				System.out.println("Book with same ID exists.");
				return;
			}
		}
		catalog.add(book);
		System.out.println("Book added successfully.");
	}

	public void removeBook(Book book) {
		if (book.isIssued()) {
			System.out.println("Book is currently issued and cannot be removed.");
			return;
		}
		catalog.remove(book);
		System.out.println("Book removed successfully.");
	}

	public void issueBook(Book book, Member member) {
		if (!book.isIssued() && member.getCurrentlyIssuedBooks().size() < member.getMaxBooksAllowed()) {
			book.setIssued(true);
			book.setIssuedTo(member);
			book.setDueDate(LocalDate.now().plusDays(member.getMaxAllowedDays()));
			member.getCurrentlyIssuedBooks().add(book);
			System.out.println("Book issued to " + member.getName());
		} else {
			System.out.println("Cannot issue book.");
		}
	}

	public void returnBook(Book book, Member member) {
		if (book.getIssuedTo() == member) {
			book.setIssued(false);
			book.setIssuedTo(null);
			book.setDueDate(null);
			member.getCurrentlyIssuedBooks().remove(book);
			if (!book.getReservationQueue().isEmpty()) {
				Member next = book.getReservationQueue().poll();
				issueBook(book, next);
			}
			System.out.println("Book returned successfully.");
		} else {
			System.out.println("This member did not issue the book.");
		}
	}

	public void reserveBook(Book book, Member member) {
		if (book.isIssued()) {
			book.getReservationQueue().add(member);
			System.out.println("Book reserved successfully.");
		} else {
			System.out.println("Book is available. No need to reserve.");
		}
	}

	public void viewIssuedBooks(Member member) {
		for (Book book : member.getCurrentlyIssuedBooks()) {
			int daysLeft = book.getDueDate().compareTo(LocalDate.now());
			System.out.println("Book: " + book.getTitle() + ", Due in: " + daysLeft + " days");
		}
	}

	public void searchBooks(String keyword) {
		for (Book book : catalog) {
			if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())
					|| book.getAuthor().toLowerCase().contains(keyword.toLowerCase())
					|| book.getGenre().toLowerCase().contains(keyword.toLowerCase())) {
				String status = book.isIssued() ? "Issued" : "Available";
				System.out.println(book.getTitle() + " by " + book.getAuthor() + " [" + status + "]");
			}
		}
	}

	public void viewOverdueBooks() {
		for (Member member : members) {
			for (Book book : member.getCurrentlyIssuedBooks()) {
				if (book.getDueDate().isBefore(LocalDate.now())) {
					System.out.println("Overdue: " + book.getTitle() + " | Member: " + member.getName());
				}
			}
		}
	}
}

public class LibraryManagementSystem {
	public static void main(String[] args) {
		Librarian librarian = new Librarian("Admin", "admin@lib.com", "1234567890");

		Book book1 = new Book("Java Programming", "James Gosling", "Programming");
		Book book2 = new Book("Effective Java", "Joshua Bloch", "Programming");

		librarian.addBook(book1);
		librarian.addBook(book2);

		Member student = new StudentMember("Alice", "alice@gmail.com", "1112223333");
		librarian.registerMember(student);

		librarian.issueBook(book1, student);

		librarian.viewIssuedBooks(student);

		librarian.returnBook(book1, student);

		librarian.searchBooks("Java");
	}
}