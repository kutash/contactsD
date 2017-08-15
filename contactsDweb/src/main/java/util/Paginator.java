package util;

import java.util.Set;
import java.util.TreeSet;

public class Paginator {

    private boolean prevPageActive = true;
    private boolean nextPageActive = true;
    private Set<Integer> pages;
    private int currentPage;
    private int totalPages;

    private Paginator(int targetPage, int totalPages) {

        this.totalPages = totalPages;
        currentPage = targetPage;
        if (targetPage == 1) {
            setPrevPageActive();
        }
        if (targetPage == totalPages) {
            setNextPageActive();
        }
        setPages(getPages(targetPage, totalPages));
    }

    public static Paginator getPaginator(int targetPage, int totalPages) {
        return new Paginator(targetPage, totalPages);
    }

    private Set<Integer> getPages(int targetPage, int totalPages) {

        final int MAX_PAGES = 9;
        Set<Integer> pagesList = new TreeSet<>();
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

    private void setPrevPageActive() {
        this.prevPageActive = false;
    }

    public boolean isNextPageActive() {
        return nextPageActive;
    }

    private void setNextPageActive() {
        this.nextPageActive = false;
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