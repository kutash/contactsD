package paginator;

import java.util.Set;
import java.util.TreeSet;

public class Paginator {

    private boolean prevPageActive = true;
    private boolean nextPageActive = true;
    private Set<Integer> pages;
    private int currentPage;
    private int totalPages;

    private final int MAX_PAGES = 9;

    private Paginator(int targetPage, int totalPages) {
        this.totalPages = totalPages;
        currentPage = targetPage;
        if (targetPage == 1) {
            setPrevPageActive(false);
        }
        if (targetPage == totalPages) {
            setNextPageActive(false);
        }
        setPages(getPages(targetPage, totalPages));
    }

    public static Paginator getPaginator(int targetPage, int totalPages) {
        return new Paginator(targetPage, totalPages);
    }

    private Set<Integer> getPages(int targetPage, int totalPages) {
        Set<Integer> pagesList = new TreeSet<Integer>();
        if (totalPages < MAX_PAGES + 1) {
            for (int i = 1; i <= totalPages; i++) {
                pagesList.add(i);
            }
            return pagesList;
        } else {
            if (targetPage > totalPages - MAX_PAGES / 2) {
                for (int i = totalPages; i > totalPages - MAX_PAGES; i--) {
                    pagesList.add(i);
                }
                return pagesList;
            } else if (targetPage < Math.ceil(MAX_PAGES / 2) + 1) {
                for (int i = 1; i <= MAX_PAGES; i++) {
                    pagesList.add(i);
                }
                return pagesList;
            }
            for (int i = targetPage - MAX_PAGES / 2; i <= targetPage + MAX_PAGES / 2; i++) {
                pagesList.add(i);
            }
            return pagesList;
        }
    }

    public boolean isPrevPageActive() {
        return prevPageActive;
    }

    public void setPrevPageActive(boolean prevPageActive) {
        this.prevPageActive = prevPageActive;
    }

    public boolean isNextPageActive() {
        return nextPageActive;
    }

    private void setNextPageActive(boolean nextPageActive) {
        this.nextPageActive = nextPageActive;
    }

    public Set<Integer> getPages() {
        return pages;
    }

    private void setPages(Set<Integer> pages) {
        this.pages = pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}