package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.ImportFlag;
import io.realm.ProxyUtils;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.NativeContext;
import io.realm.internal.OsList;
import io.realm.internal.OsMap;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.OsSet;
import io.realm.internal.Property;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.internal.core.NativeRealmAny;
import io.realm.internal.objectstore.OsObjectBuilder;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("all")
public class com_example_gtn_ImageDBRealmProxy extends com.example.gtn.ImageDB
    implements RealmObjectProxy, com_example_gtn_ImageDBRealmProxyInterface {

    static final class ImageDBColumnInfo extends ColumnInfo {
        long pkColKey;
        long imgNameColKey;
        long imgTypeColKey;
        long personalInfoColKey;
        long shootingTimeColKey;

        ImageDBColumnInfo(OsSchemaInfo schemaInfo) {
            super(5);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("ImageDB");
            this.pkColKey = addColumnDetails("pk", "pk", objectSchemaInfo);
            this.imgNameColKey = addColumnDetails("imgName", "imgName", objectSchemaInfo);
            this.imgTypeColKey = addColumnDetails("imgType", "imgType", objectSchemaInfo);
            this.personalInfoColKey = addColumnDetails("personalInfo", "personalInfo", objectSchemaInfo);
            this.shootingTimeColKey = addColumnDetails("shootingTime", "shootingTime", objectSchemaInfo);
        }

        ImageDBColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new ImageDBColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final ImageDBColumnInfo src = (ImageDBColumnInfo) rawSrc;
            final ImageDBColumnInfo dst = (ImageDBColumnInfo) rawDst;
            dst.pkColKey = src.pkColKey;
            dst.imgNameColKey = src.imgNameColKey;
            dst.imgTypeColKey = src.imgTypeColKey;
            dst.personalInfoColKey = src.personalInfoColKey;
            dst.shootingTimeColKey = src.shootingTimeColKey;
        }
    }

    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private ImageDBColumnInfo columnInfo;
    private ProxyState<com.example.gtn.ImageDB> proxyState;

    com_example_gtn_ImageDBRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (ImageDBColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.example.gtn.ImageDB>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$pk() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.pkColKey);
    }

    @Override
    public void realmSet$pk(String value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'pk' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$imgName() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.imgNameColKey);
    }

    @Override
    public void realmSet$imgName(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'imgName' to null.");
            }
            row.getTable().setString(columnInfo.imgNameColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'imgName' to null.");
        }
        proxyState.getRow$realm().setString(columnInfo.imgNameColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$imgType() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.imgTypeColKey);
    }

    @Override
    public void realmSet$imgType(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'imgType' to null.");
            }
            row.getTable().setString(columnInfo.imgTypeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'imgType' to null.");
        }
        proxyState.getRow$realm().setString(columnInfo.imgTypeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$personalInfo() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.personalInfoColKey);
    }

    @Override
    public void realmSet$personalInfo(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'personalInfo' to null.");
            }
            row.getTable().setString(columnInfo.personalInfoColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'personalInfo' to null.");
        }
        proxyState.getRow$realm().setString(columnInfo.personalInfoColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$shootingTime() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.shootingTimeColKey);
    }

    @Override
    public void realmSet$shootingTime(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'shootingTime' to null.");
            }
            row.getTable().setString(columnInfo.shootingTimeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'shootingTime' to null.");
        }
        proxyState.getRow$realm().setString(columnInfo.shootingTimeColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder(NO_ALIAS, "ImageDB", false, 5, 0);
        builder.addPersistedProperty(NO_ALIAS, "pk", RealmFieldType.STRING, Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty(NO_ALIAS, "imgName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty(NO_ALIAS, "imgType", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty(NO_ALIAS, "personalInfo", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty(NO_ALIAS, "shootingTime", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static ImageDBColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new ImageDBColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "ImageDB";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "ImageDB";
    }

    @SuppressWarnings("cast")
    public static com.example.gtn.ImageDB createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.example.gtn.ImageDB obj = null;
        if (update) {
            Table table = realm.getTable(com.example.gtn.ImageDB.class);
            ImageDBColumnInfo columnInfo = (ImageDBColumnInfo) realm.getSchema().getColumnInfo(com.example.gtn.ImageDB.class);
            long pkColumnKey = columnInfo.pkColKey;
            long objKey = Table.NO_MATCH;
            if (!json.isNull("pk")) {
                objKey = table.findFirstString(pkColumnKey, json.getString("pk"));
            }
            if (objKey != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(objKey), realm.getSchema().getColumnInfo(com.example.gtn.ImageDB.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_example_gtn_ImageDBRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("pk")) {
                if (json.isNull("pk")) {
                    obj = (io.realm.com_example_gtn_ImageDBRealmProxy) realm.createObjectInternal(com.example.gtn.ImageDB.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_example_gtn_ImageDBRealmProxy) realm.createObjectInternal(com.example.gtn.ImageDB.class, json.getString("pk"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'pk'.");
            }
        }

        final com_example_gtn_ImageDBRealmProxyInterface objProxy = (com_example_gtn_ImageDBRealmProxyInterface) obj;
        if (json.has("imgName")) {
            if (json.isNull("imgName")) {
                objProxy.realmSet$imgName(null);
            } else {
                objProxy.realmSet$imgName((String) json.getString("imgName"));
            }
        }
        if (json.has("imgType")) {
            if (json.isNull("imgType")) {
                objProxy.realmSet$imgType(null);
            } else {
                objProxy.realmSet$imgType((String) json.getString("imgType"));
            }
        }
        if (json.has("personalInfo")) {
            if (json.isNull("personalInfo")) {
                objProxy.realmSet$personalInfo(null);
            } else {
                objProxy.realmSet$personalInfo((String) json.getString("personalInfo"));
            }
        }
        if (json.has("shootingTime")) {
            if (json.isNull("shootingTime")) {
                objProxy.realmSet$shootingTime(null);
            } else {
                objProxy.realmSet$shootingTime((String) json.getString("shootingTime"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.example.gtn.ImageDB createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.example.gtn.ImageDB obj = new com.example.gtn.ImageDB();
        final com_example_gtn_ImageDBRealmProxyInterface objProxy = (com_example_gtn_ImageDBRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("pk")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$pk((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$pk(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("imgName")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$imgName((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$imgName(null);
                }
            } else if (name.equals("imgType")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$imgType((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$imgType(null);
                }
            } else if (name.equals("personalInfo")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$personalInfo((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$personalInfo(null);
                }
            } else if (name.equals("shootingTime")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$shootingTime((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$shootingTime(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'pk'.");
        }
        return realm.copyToRealmOrUpdate(obj);
    }

    static com_example_gtn_ImageDBRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.example.gtn.ImageDB.class), false, Collections.<String>emptyList());
        io.realm.com_example_gtn_ImageDBRealmProxy obj = new io.realm.com_example_gtn_ImageDBRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.example.gtn.ImageDB copyOrUpdate(Realm realm, ImageDBColumnInfo columnInfo, com.example.gtn.ImageDB object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null) {
            final BaseRealm otherRealm = ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm();
            if (otherRealm.threadId != realm.threadId) {
                throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
            }
            if (otherRealm.getPath().equals(realm.getPath())) {
                return object;
            }
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.example.gtn.ImageDB) cachedRealmObject;
        }

        com.example.gtn.ImageDB realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.example.gtn.ImageDB.class);
            long pkColumnKey = columnInfo.pkColKey;
            long objKey = table.findFirstString(pkColumnKey, ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$pk());
            if (objKey == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(objKey), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_example_gtn_ImageDBRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.example.gtn.ImageDB copy(Realm realm, ImageDBColumnInfo columnInfo, com.example.gtn.ImageDB newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.example.gtn.ImageDB) cachedRealmObject;
        }

        com_example_gtn_ImageDBRealmProxyInterface unmanagedSource = (com_example_gtn_ImageDBRealmProxyInterface) newObject;

        Table table = realm.getTable(com.example.gtn.ImageDB.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.pkColKey, unmanagedSource.realmGet$pk());
        builder.addString(columnInfo.imgNameColKey, unmanagedSource.realmGet$imgName());
        builder.addString(columnInfo.imgTypeColKey, unmanagedSource.realmGet$imgType());
        builder.addString(columnInfo.personalInfoColKey, unmanagedSource.realmGet$personalInfo());
        builder.addString(columnInfo.shootingTimeColKey, unmanagedSource.realmGet$shootingTime());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_example_gtn_ImageDBRealmProxy managedCopy = newProxyInstance(realm, row);
        cache.put(newObject, managedCopy);

        return managedCopy;
    }

    public static long insert(Realm realm, com.example.gtn.ImageDB object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.example.gtn.ImageDB.class);
        long tableNativePtr = table.getNativePtr();
        ImageDBColumnInfo columnInfo = (ImageDBColumnInfo) realm.getSchema().getColumnInfo(com.example.gtn.ImageDB.class);
        long pkColumnKey = columnInfo.pkColKey;
        long objKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$pk();
        if (primaryKeyValue != null) {
            objKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (objKey == Table.NO_MATCH) {
            objKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, objKey);
        String realmGet$imgName = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$imgName();
        if (realmGet$imgName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imgNameColKey, objKey, realmGet$imgName, false);
        }
        String realmGet$imgType = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$imgType();
        if (realmGet$imgType != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imgTypeColKey, objKey, realmGet$imgType, false);
        }
        String realmGet$personalInfo = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$personalInfo();
        if (realmGet$personalInfo != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.personalInfoColKey, objKey, realmGet$personalInfo, false);
        }
        String realmGet$shootingTime = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$shootingTime();
        if (realmGet$shootingTime != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.shootingTimeColKey, objKey, realmGet$shootingTime, false);
        }
        return objKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.gtn.ImageDB.class);
        long tableNativePtr = table.getNativePtr();
        ImageDBColumnInfo columnInfo = (ImageDBColumnInfo) realm.getSchema().getColumnInfo(com.example.gtn.ImageDB.class);
        long pkColumnKey = columnInfo.pkColKey;
        com.example.gtn.ImageDB object = null;
        while (objects.hasNext()) {
            object = (com.example.gtn.ImageDB) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long objKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$pk();
            if (primaryKeyValue != null) {
                objKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (objKey == Table.NO_MATCH) {
                objKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, objKey);
            String realmGet$imgName = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$imgName();
            if (realmGet$imgName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imgNameColKey, objKey, realmGet$imgName, false);
            }
            String realmGet$imgType = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$imgType();
            if (realmGet$imgType != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imgTypeColKey, objKey, realmGet$imgType, false);
            }
            String realmGet$personalInfo = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$personalInfo();
            if (realmGet$personalInfo != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.personalInfoColKey, objKey, realmGet$personalInfo, false);
            }
            String realmGet$shootingTime = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$shootingTime();
            if (realmGet$shootingTime != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.shootingTimeColKey, objKey, realmGet$shootingTime, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.example.gtn.ImageDB object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.example.gtn.ImageDB.class);
        long tableNativePtr = table.getNativePtr();
        ImageDBColumnInfo columnInfo = (ImageDBColumnInfo) realm.getSchema().getColumnInfo(com.example.gtn.ImageDB.class);
        long pkColumnKey = columnInfo.pkColKey;
        long objKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$pk();
        if (primaryKeyValue != null) {
            objKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (objKey == Table.NO_MATCH) {
            objKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        }
        cache.put(object, objKey);
        String realmGet$imgName = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$imgName();
        if (realmGet$imgName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imgNameColKey, objKey, realmGet$imgName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.imgNameColKey, objKey, false);
        }
        String realmGet$imgType = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$imgType();
        if (realmGet$imgType != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imgTypeColKey, objKey, realmGet$imgType, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.imgTypeColKey, objKey, false);
        }
        String realmGet$personalInfo = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$personalInfo();
        if (realmGet$personalInfo != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.personalInfoColKey, objKey, realmGet$personalInfo, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.personalInfoColKey, objKey, false);
        }
        String realmGet$shootingTime = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$shootingTime();
        if (realmGet$shootingTime != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.shootingTimeColKey, objKey, realmGet$shootingTime, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.shootingTimeColKey, objKey, false);
        }
        return objKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.gtn.ImageDB.class);
        long tableNativePtr = table.getNativePtr();
        ImageDBColumnInfo columnInfo = (ImageDBColumnInfo) realm.getSchema().getColumnInfo(com.example.gtn.ImageDB.class);
        long pkColumnKey = columnInfo.pkColKey;
        com.example.gtn.ImageDB object = null;
        while (objects.hasNext()) {
            object = (com.example.gtn.ImageDB) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long objKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$pk();
            if (primaryKeyValue != null) {
                objKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (objKey == Table.NO_MATCH) {
                objKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            }
            cache.put(object, objKey);
            String realmGet$imgName = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$imgName();
            if (realmGet$imgName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imgNameColKey, objKey, realmGet$imgName, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.imgNameColKey, objKey, false);
            }
            String realmGet$imgType = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$imgType();
            if (realmGet$imgType != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imgTypeColKey, objKey, realmGet$imgType, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.imgTypeColKey, objKey, false);
            }
            String realmGet$personalInfo = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$personalInfo();
            if (realmGet$personalInfo != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.personalInfoColKey, objKey, realmGet$personalInfo, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.personalInfoColKey, objKey, false);
            }
            String realmGet$shootingTime = ((com_example_gtn_ImageDBRealmProxyInterface) object).realmGet$shootingTime();
            if (realmGet$shootingTime != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.shootingTimeColKey, objKey, realmGet$shootingTime, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.shootingTimeColKey, objKey, false);
            }
        }
    }

    public static com.example.gtn.ImageDB createDetachedCopy(com.example.gtn.ImageDB realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.example.gtn.ImageDB unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.example.gtn.ImageDB();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.example.gtn.ImageDB) cachedObject.object;
            }
            unmanagedObject = (com.example.gtn.ImageDB) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_example_gtn_ImageDBRealmProxyInterface unmanagedCopy = (com_example_gtn_ImageDBRealmProxyInterface) unmanagedObject;
        com_example_gtn_ImageDBRealmProxyInterface realmSource = (com_example_gtn_ImageDBRealmProxyInterface) realmObject;
        Realm objectRealm = (Realm) ((RealmObjectProxy) realmObject).realmGet$proxyState().getRealm$realm();
        unmanagedCopy.realmSet$pk(realmSource.realmGet$pk());
        unmanagedCopy.realmSet$imgName(realmSource.realmGet$imgName());
        unmanagedCopy.realmSet$imgType(realmSource.realmGet$imgType());
        unmanagedCopy.realmSet$personalInfo(realmSource.realmGet$personalInfo());
        unmanagedCopy.realmSet$shootingTime(realmSource.realmGet$shootingTime());

        return unmanagedObject;
    }

    static com.example.gtn.ImageDB update(Realm realm, ImageDBColumnInfo columnInfo, com.example.gtn.ImageDB realmObject, com.example.gtn.ImageDB newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_example_gtn_ImageDBRealmProxyInterface realmObjectTarget = (com_example_gtn_ImageDBRealmProxyInterface) realmObject;
        com_example_gtn_ImageDBRealmProxyInterface realmObjectSource = (com_example_gtn_ImageDBRealmProxyInterface) newObject;
        Table table = realm.getTable(com.example.gtn.ImageDB.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);
        builder.addString(columnInfo.pkColKey, realmObjectSource.realmGet$pk());
        builder.addString(columnInfo.imgNameColKey, realmObjectSource.realmGet$imgName());
        builder.addString(columnInfo.imgTypeColKey, realmObjectSource.realmGet$imgType());
        builder.addString(columnInfo.personalInfoColKey, realmObjectSource.realmGet$personalInfo());
        builder.addString(columnInfo.shootingTimeColKey, realmObjectSource.realmGet$shootingTime());

        builder.updateExistingTopLevelObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("ImageDB = proxy[");
        stringBuilder.append("{pk:");
        stringBuilder.append(realmGet$pk());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{imgName:");
        stringBuilder.append(realmGet$imgName());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{imgType:");
        stringBuilder.append(realmGet$imgType());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{personalInfo:");
        stringBuilder.append(realmGet$personalInfo());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{shootingTime:");
        stringBuilder.append(realmGet$shootingTime());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long objKey = proxyState.getRow$realm().getObjectKey();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (objKey ^ (objKey >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com_example_gtn_ImageDBRealmProxy aImageDB = (com_example_gtn_ImageDBRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aImageDB.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aImageDB.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aImageDB.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
