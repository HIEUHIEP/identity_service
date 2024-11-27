#DI (dependency injection): Tiêm sự phụ thuộc.
Nó là new đối tượng. Nhưng là được truyền từ bên ngoài chứ không new trực tiếp.

#IoC (inversion of control) : Đảo ngược sự kiểm soát.
Các đối tượng sẽ không được quản lý bởi Application nữa. Thay vào đó là Spring IoC container.
Khi cần IoC container sẽ gán (tiêm) sự phụ thuộc cho các đối tượng.
IoC container sẽ tìm đối tượng phù hợp nhất đề tiêm.
Hay dùng với @Autowired, Constructor injection, Setter injection, Interface injection

#Bean Scope:
Singleton (Default): Một instance duy nhất trong toàn bộ Spring container.
Prototype: Mỗi lần yêu cầu tạo một instance mới.
Request: Mỗi HTTP request có một instance riêng biệt.
Session: Mỗi HTTP session có một instance riêng biệt.
Global Session: Mỗi global HTTP session có một instance riêng biệt.
Application: Mỗi ServletContext có một instance riêng biệt