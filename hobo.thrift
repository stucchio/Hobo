namespace java org.styloot.hobo.gen

typedef string ItemId

service Hobo {
  list<ItemId> find(1:string category_name, 2:list<string> features, 3:i32 page),
}