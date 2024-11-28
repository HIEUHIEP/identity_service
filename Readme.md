#DI (dependency injection): Tiêm sự phụ thuộc.
Nó là new đối tượng. Nhưng là được truyền từ bên ngoài chứ không new trực tiếp.
Cách truyền thì có thể là Setter, Constructor...

#IoC (inversion of control) : Đảo ngược sự kiểm soát.
Các đối tượng sẽ không được quản lý bởi Application nữa. Thay vào đó là Spring IoC container.
Khi cần IoC container sẽ gán (tiêm) sự phụ thuộc cho các đối tượng.
IoC container sẽ tìm đối tượng phù hợp nhất đề tiêm.
Hay dùng với @Autowired, Constructor injection, Setter injection, Interface injection

#Bean Scope:
Singleton (Default): Một instance duy nhất trong toàn bộ Spring container. (tạo khi start app)
Prototype: Mỗi lần yêu cầu tạo một instance mới.
Request: Mỗi HTTP request có một instance riêng biệt.
Session: Mỗi HTTP session có một instance riêng biệt.
Global Session: Mỗi global HTTP session có một instance riêng biệt.
Application: Mỗi ServletContext có một instance riêng biệt

@ComponentScan("com.devteria.identity_service") : chỉ định package mà IoC có thể quét Bean.
@SpringBootApplication(scanBasePackages = "com.devteria.identity_service"): tương tự.

Khởi tạo Bean:
#Eager ( háo hức ): khởi tạo ngay khi start app (ví dụ singleton scope)
#Lazy : chỉ tạo khi chúng ta gọi nó. (ví dụ prototype scope)

Tên bean mặt định là tên class (viết thường chữ đâu)
Thay đổi tên của Bean bằng cách @Component("tên bean")

@Primary: Đánh dấu Bean này là ưu tiên.
Khi trogn IoC có >=2 bean cùng loại. thì bean đc đánh dấu @Primary sẽ được ưu tiên dùng để Inject

@Qualifier: Gán cố định khi Injection sẽ dùng bean nào.