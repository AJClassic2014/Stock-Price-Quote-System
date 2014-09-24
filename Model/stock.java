package Model;
/*interface stock
 * this is a interface 
 * methods: attach() detach() notifyobserver()
 */
public interface stock 
{
	public void attach(String myMonType);
	public void detach(String myMonType);
	public void notifyobserver();
}
