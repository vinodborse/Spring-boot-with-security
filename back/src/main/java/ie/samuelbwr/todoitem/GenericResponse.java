package ie.samuelbwr.todoitem;

/**
 * @version 1.0 06-Apr-2016
 */
public class GenericResponse {
  private String response;

  
  public GenericResponse() {
    super();
  }
  
  public GenericResponse(String response) {
    this.response = response;
  }

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }
}
