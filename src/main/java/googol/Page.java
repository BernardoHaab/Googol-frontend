package googol;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Page implements Serializable {
  private static final long serialVersionUID = 1L;

  private int id;

  private String url;

  private String title;

  private String quote;

  private Set<Page> referencedBy;

  private Set<Page> referencePages;

  public Page(String url, Set<Page> referencedBy, Set<Page> referencePages) {
    this.url = url;
    this.referencedBy = referencedBy;
    this.referencePages = referencePages;
  }

  public Page(Set<Page> referencedBy, String url) {
    this.referencedBy = referencedBy;
    this.url = url;
  }

  public Page(String url) {
    this.url = url;
    this.referencedBy = new HashSet<>();
    this.referencePages = new HashSet<>();
  }

  public Page() {

  }

  public int getId() {
    return id;
  }

  public String getUrl() {
    return url;
  }

  public String getTitle() {
    return title;
  }

  public String getQuote() {
    return quote;
  }

  public Set<Page> getReferencedBy() {
    return referencedBy;
  }

  public Set<Page> getReferencePages() {
    return referencePages;
  }

  public void addReference(Page page) {
    page.referencedBy.add(this);
    referencePages.add(page);
  }

  public void addReferencedBy(Page page) {
    this.referencedBy.add(page);
  }

  public String toString() {
    return "Page{" +
        "id=" + id +
        ", url='" + url + '\'' +
        ", referencedBy=" + referencedBy.size() +
        ", referencePages=" + referencePages.size() +
        '}';
  }
  //
  // public static Page getPageByUrl(String url, Session session) {
  // Page page = null;
  // try {
  // TypedQuery<Page> q = session.createNamedQuery("Page.byUrl", Page.class);
  // q.setParameter("url", url);
  // page = q.getSingleResult();
  // } catch (NoResultException e) {
  // session.beginTransaction();
  // page = new Page(url);
  // session.persist(page);
  // session.getTransaction().commit();
  // } catch (Exception e) {
  // System.out.println("Error persisting page");
  // e.printStackTrace();
  // session.beginTransaction();
  // page = new Page(url);
  // session.persist(page);
  // session.getTransaction().commit();
  // }
  // return page;
  // }

}
