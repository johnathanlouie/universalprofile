package combiner;

import entity.Person;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import rest.Rest;

/**
 *
 * @author sashi
 */
public class StartCombiner {

	public CombinerEngine ce;

	public StartCombiner() {
		this.ce = new CombinerEngine(new SpecialPersonChecker());
	}

	public LinkedList<Person> combine(LinkedList<LinkedList> collection) {
		for (int i = 0; i < collection.size(); i++) {
			this.ce.addCollection(collection.get(i));
		}
		return this.ce.combineAll();
	}

	public LinkedList start(String coll1, String coll2) {
		LinkedList<LinkedList> collec;
		LinkedList<Person> combined, c1, c2;
		collec = new LinkedList();
		c1 = this.retrieveProfiles(coll1);
		c2 = this.retrieveProfiles(coll2);
		if (c1 != null && c1.size() > 0) {
			collec.add(c1);
		}
		if (c2 != null && c2.size() > 0) {
			collec.add(c2);
		}
		combined = this.combine(collec);
		return combined;
	}

	public LinkedList retrieveProfiles(String nameCollec) {
		LinkedList prof = null;

		try {
			String json = Rest.getAll(nameCollec);
			//System.out.println(Rest.getAll(nameCollec));
			System.out.println(json);
			prof = getAllPerson(json);
		} catch (Exception ex) {
			System.out.println(ex.toString());
			Logger.getLogger(StartCombiner.class.getName()).log(Level.SEVERE, null, ex);
		}
		return prof;
	}

	public static Person getPerson(JSONObject obj) {
		Person p = new Person();
		JSONObject jO = null;
		String first, last, city, state, phone, email;
		first = "";
		last = "";
		city = "";
		state = "";
		phone = "";
		email = "";
		Iterator it = obj.keys();
		while (it.hasNext()) {
			String curK = (String) it.next();
			if (curK.equals("name")) {

				JSONObject temp = (JSONObject) obj.get("name");
				Iterator itTmp = temp.keys();
				while (itTmp.hasNext()) {
					String temp2 = (String) itTmp.next();
					if (temp2.equals("first")) {
						first = (String) temp.get("first");
					}
					if (temp2.equals("last")) {
						last = (String) temp.get("last");
					}

				}
			} else if (curK.equals("address")) {
				System.out.println(curK);
				System.out.println();
				JSONArray temp = (JSONArray) obj.get("address");
				for (int i = 0; i < temp.length(); i++) {
					JSONObject addJ = temp.getJSONObject(i);
					Iterator itTmp = addJ.keys();
					while (itTmp.hasNext()) {

						String temp2 = (String) itTmp.next();
						if (temp2.equals("city")) {
							city = (String) addJ.get("city");
						}
						if (temp2.equals("state")) {
							state = (String) addJ.get("state");
						}

					}
				}

			} else if (curK.equals("email")) {
				JSONArray temp = (JSONArray) obj.get("email");
				Iterator itTemp = temp.iterator();
				while (itTemp.hasNext()) {
					email = (String) itTemp.next();
					System.out.println(email);
					p.addEmail(email);
				}

			}
		}

		p.setFirstName(first);
		p.setLastName(last);
		p.setCity(city);
		p.setState(state);
		return p;
	}

	public static LinkedList<Person> getAllPerson(String json) {
		LinkedList<Person> listP = new LinkedList();
		JSONArray jar = new JSONArray(json);
		for (int i = 0; i < jar.length(); i++) {
			Person curP = getPerson(jar.getJSONObject(i));
			if (curP != null) {
				listP.add(curP);
			}
		}

		return listP;
	}

	public static String getJSON(LinkedList<Person> list) {
		StringBuilder json = new StringBuilder();
		json.append('[');
		for (Person p : list) {
			json.append(p.toJSON());
			json.append(',');
		}
		json.deleteCharAt(json.length() - 1);
		json.append(']');
		return json.toString();
	}

	public static void main(String[] args) {
		if (args.length == 2) {
			System.out.println("Got args");
			StartCombiner sC = new StartCombiner();
			LinkedList com = sC.start(args[0], args[1]);

			try {
				//make db entry
				Rest.insert("combined1", getJSON(com));
			} catch (Exception ex) {
				Logger.getLogger(StartCombiner.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
		try {
			//String json = Rest.getAll("test");
			String data = "[{"
					+ "\"name\":{\"first\":\"Sashi\",\"last\":\"Thapaliya\"},"
					+ "\"address\":[{\"city\":\"El Cerrito\",\"state\":\"CA\"}],"
					+ "\"email\":[\"email@email.com\"]}]";
			System.out.println(data);
			//Rest.insert("test",data);
			//System.out.println(json);
			LinkedList<Person> lP = getAllPerson(data);
			for (int i = 0; i < lP.size(); i++) {
				System.out.println(lP.get(i));
			}
			System.out.println(getJSON(lP));
			/*StartCombiner sC = new StartCombiner();
            LinkedList com = sC.start("facebook", "googleplus");
            //LinkedList<Person> pL =  sC.retrieveProfiles("facebook");
            System.out.println(com.size());
            for(int i=0; i<com.size(); i++)
            {
            System.out.println(com.get(i));
            }
            try {
            Rest.insert("combined1", getJSON(com));
            } catch (Exception ex) {
            Logger.getLogger(StartCombiner.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(getJSON(com));*/
		} catch (Exception ex) {
			Logger.getLogger(StartCombiner.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
