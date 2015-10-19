package features

object Vars {

  val SERVICE_TEST_URI: String = "http://localhost:8080/q/wapi"

  val Q_DATA_SOURCE: String = "q-datasource.properties"

  val DATA_FILE_ROOT_FOLDER: String = "/data/"
  val DATA_FILE_EMPTY: String = DATA_FILE_ROOT_FOLDER + "empty.xml"
  val REF_CATALOGUE: String = DATA_FILE_ROOT_FOLDER + "catalogue_ref.xml"
  val DATA_FILE_CATALOGUE_NEW: String = DATA_FILE_ROOT_FOLDER + "catalogue_new.xml"
  val DATA_FILE_FEATURE_SET_REF: String = DATA_FILE_ROOT_FOLDER + "feature_set_ref.xml"
  val DATA_FILE_FEATURE_SET_NEW: String = DATA_FILE_ROOT_FOLDER + "feature_set_new.xml"
  val DATA_FILE_FEATURE_REF: String = DATA_FILE_ROOT_FOLDER + "feature_ref.xml"

  val REF_CATALOGUE_ID: String = "catalogue_id"
  val KEY_FEATURE_SET_ID: String = "feature_set_id"

  val METHOD_SET_SERVICE_URI: String = "setServiceURI"
  val METHOD_CREATE_CATALOGUE_PAYLOAD: String = "createCataloguePayload"
  val METHOD_SVC_CREATE_CATALOGUE: String = "createCatalogue"
  val METHOD_SVC_CREATE_FEATURE_SET: String = "createFeatureSet"
  val METHOD_CREATE_FEATURE_SET_PAYLOAD: String = "createFeatureSetPayload"
  val METHOD_SVC_CREATE_FEATURE: String = "createFeature"
  val METHOD_CREATE_FEATURE_PAYLOAD: String = "createFeaturePayload"

  val QUERY_GET_CATALOGUE_BY_ID = "SELECT * FROM Catalogue WHERE id = '%s'"
}
