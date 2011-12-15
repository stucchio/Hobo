namespace java org.styloot.hobo.gen

typedef string ItemId

exception NoSuchCategoryException {
  1: string category_name
}

exception NoSuchFeatureException {
  1: string feature_name
}

service Hobo {
  list<ItemId> findByCategory(1:string category_name, 2:i32 page) throws (1:NoSuchCategoryException nsc, 2:NoSuchFeatureException nsf),
  list<ItemId> findByFeatures(1:list<string> features , 2:i32 page) throws (1:NoSuchCategoryException nsc, 2:NoSuchFeatureException nsf),
}