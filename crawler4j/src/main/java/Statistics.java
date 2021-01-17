public class Statistics {
    private int totalPageCount = 0;
    private int totalLinksCount = 0;

    public void addPageCount() {
        totalPageCount++;
    }

    public void addLinksCount(int linksCount) {
        totalLinksCount += linksCount;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public int getTotalLinksCount() {
        return totalLinksCount;
    }
}
