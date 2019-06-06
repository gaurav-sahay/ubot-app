package com.sap.ubot.notification.sender;

//@Component
public class TestingCache {/*
	
	@Autowired
	private ConcurrentMapCacheManager concurrentMapCacheManager;
	
	
	
	@Cacheable(value = "mycache", key="#pid",sync=true)
	public List<Book> getBook(String pid) {
		System.out.println("Executing getBook method...");
		Book book = new Book();
		book.setId(id);		
		book.setName("Mahabharat");
		return prepareBooks(pid);
	}
	
	@CachePut(value = "mycache", key="#book.id")
	public List<Book> updateBook(Book book) throws InterruptedException {
		//Thread.sleep(2000);
		System.out.println("Executing updateBook method...");
		Cache cache = concurrentMapCacheManager.getCache("mycache");
		List<Book> books = (List<Book>) cache.get(book.getId()).get();
		books.add(new Book(book.getId(), book.getName()));
		return books;
	}
	
	@CachePut(value = "mycache", key="#book.id")
	public List<Book> deleteBook(Book book) {
		System.out.println("Executing deleteBook method...");
		Cache cache = concurrentMapCacheManager.getCache("mycache");
		List<Book> books = (List<Book>) cache.get(book.getId()).get();
		books.remove(book);
		return books;
	}
	
	
	private List<Book> prepareBooks(String pid){
		List<Book> books = new ArrayList<>();
		for(int i=0;i<5;i++) {
			books.add(new Book(""+i,"Book_"+pid));
		}
		return books;
	}
	

*/}
