import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.jongo.Jongo;

public abstract class Database {

	protected static final String TEST_DB_NAME = "DB_TEST";
	private static MongoClient mongoClient;
	private final Morphia morphia = new Morphia();
	private final DB db;
	private static MongoDatabase database = null;
	private final Datastore ds;
	private Jongo jo;


	protected Database() {
		this(new MongoClient(new MongoClientURI(getMongoURI())));
	}

	protected Database(final MongoClient mongoClient) {
		this.mongoClient = mongoClient;
		this.db = getMongoClient().getDB(TEST_DB_NAME);
		this.database = getMongoClient().getDatabase(TEST_DB_NAME);
		this.ds = getMorphia().createDatastore(getMongoClient(), getDb().getName());
		this.jo = new Jongo(db);
	}
	protected static String getMongoURI() {
		return System.getProperty("MONGO_URI", "mongodb://localhost:27017");
	}

	//region Getters and Setters
	public static MongoClient getMongoClient() {
		return mongoClient;
	}

	public static void setMongoClient(MongoClient mongoClient) {
		Database.mongoClient = mongoClient;
	}

	public Morphia getMorphia() {
		return morphia;
	}

	public DB getDb() {
		return db;
	}

	public static MongoDatabase getDatabase() {
		return database;
	}

	public static void setDatabase(MongoDatabase database) {
		Database.database = database;
	}

	public Datastore getDs() {
		return ds;
	}

	public Jongo getJo() {
		return jo;
	}

	public void setJo(Jongo jo) {
		this.jo = jo;
	}
	//endregion


}
