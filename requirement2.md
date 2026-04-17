# Requirement 2 — 代码质量 + 新功能

## 学生 A — 修复现有代码中的问题

当前 `AccountService` 有以下问题需要修复：

### 1. 用自定义异常替换 `RuntimeException`

`withdraw()` 里有两处用了 `RuntimeException`，应改为项目已有的自定义异常：

| 当前代码 | 应改为 |
|----------|--------|
| `throw new RuntimeException("Account not found")` | `throw new AccountNotFoundException(...)` |
| `throw new RuntimeException("Insufficient balance")` | `throw new InsufficientBalanceException(...)` |

`filterAccountsOver1000()` 和 `sumBalancesOver1000()` 里的 `RuntimeException` 同理。

### 2. 修复 `sumBalancesOver1000` 的逻辑 Bug

当前逻辑是：先把所有账户余额加总，再判断总和是否 > 1000。  
正确逻辑应该是：**只统计余额 > 1000 的账户**，然后返回这些账户的余额之和。

### 3. 给 `withdraw()` 加上 `@Transactional`

数据库操作应该在事务中执行，避免部分更新的情况。

---

## 学生 B — 交易记录功能

### 1. 新建 `Transaction` 实体

字段：

| 字段 | 类型 | 说明 |
|------|------|------|
| `id` | Long | 主键，自增 |
| `account` | Account | 关联账户（`@ManyToOne`） |
| `type` | String | 类型：`DEPOSIT` / `WITHDRAW` / `TRANSFER` |
| `amount` | double | 发生金额 |
| `balanceBefore` | double | 操作前余额 |
| `balanceAfter` | double | 操作后余额 |
| `createdAt` | LocalDateTime | 时间戳，`@CreationTimestamp` 自动填充 |

### 2. 新建 `TransactionRepository`

继承 `JpaRepository<Transaction, Long>`，加一个方法：
```java
List<Transaction> findByAccount_Id(Long accountId);
```

### 3. 更新 `AccountService`

在 `withdraw()`、`deposit()`、`transfer()` 执行完余额变更后，保存一条 `Transaction` 记录。

### 4. 新增 Controller 接口

```
GET /accounts/{accountId}/transactions
```
返回该账户的所有交易记录列表，按时间倒序排列。

---

## 学生 C — 用户管理

### 1. 新建 DTO

**`UserRequest`**（接收创建/更新请求）：
```
name, email, password, phone, address
```

**`UserResponse`**（返回给前端，**不包含 password**）：
```
id, name, email, phone, address, role, status
```

### 2. 新建 `UserService`

```java
UserResponse createUser(UserRequest request)
UserResponse getUserById(Long userId)
UserResponse updateUser(Long userId, UserRequest request)
```

- `createUser`：新用户 `role` 默认为 `"USER"`，`status` 默认为 `"ACTIVE"`
- `getUserById`：用户不存在 → 抛出异常（自己新建 `UserNotFoundException`）
- `updateUser`：只更新 `name`、`phone`、`address`，不允许修改 `email` 和 `role`

### 3. 新建 `UserController`

```
POST   /users              创建用户，返回 201 + UserResponse
GET    /users/{userId}     查询用户信息，返回 200 + UserResponse
PUT    /users/{userId}     更新用户信息，返回 200 + UserResponse
```

加上 `@ExceptionHandler` 处理 `UserNotFoundException`，返回 404。

---

## 验收标准

| 任务 | 验收点 |
|------|--------|
| 学生 A | `withdraw` 传入不存在的账户返回 404；余额不足返回 400；`sumBalancesOver1000` 只统计余额 > 1000 的账户 |
| 学生 B | 每次 withdraw 后调用 `GET /accounts/{id}/transactions` 能看到新记录；`balanceBefore` 和 `balanceAfter` 数值正确 |
| 学生 C | 创建用户后可以用返回的 id 查询；更新只改 name/phone/address；查不存在的用户返回 404 |