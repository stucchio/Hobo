namespace java org.styloot.hobo.gen

typedef string ItemId

service Hobo {
  list<ItemId> find(1:string category_name, 2:list<string> features, 3:i32 page),
  list<ItemId> findByColor(1:string category_name, 2:list<string> features, 3:byte red, 4:byte green, 5:byte blue, 6:double colorDist, 7:i32 page), //The bytes red, green and blue are signed - basically ordinary rgb values - 128, range is [-128, 127]
}