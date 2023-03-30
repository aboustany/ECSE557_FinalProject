import java.util.ArrayList;

class RequestHistory {
  private ArrayList<String> requestHistory;

  public RequestHistory() {
    requestHistory = new ArrayList<String>();
  }

  public void addRequest(String request) {
    requestHistory.add(request);
  }

  public ArrayList<String> getRequestHistory() {
    return requestHistory;
  }

  public void clearHistory() {
    requestHistory.clear();
  }
}
