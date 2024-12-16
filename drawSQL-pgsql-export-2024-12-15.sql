CREATE TABLE "employee"(
    "id" bigserial NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "surname" VARCHAR(255) NOT NULL,
    "email" VARCHAR(255) NOT NULL,
    "password" VARCHAR(255) NOT NULL
);
CREATE INDEX "employee_email_index" ON
    "employee"("email");
CREATE INDEX "employee_name_surname_index" ON
    "employee"("name", "surname");
CREATE INDEX "employee_id_index" ON
    "employee"("id");
ALTER TABLE
    "employee" ADD PRIMARY KEY("id");
CREATE INDEX "employee_name_index" ON
    "employee"("name");
ALTER TABLE
    "employee" ADD CONSTRAINT "employee_email_unique" UNIQUE("email");
CREATE TABLE "category"(
    "id" bigserial NOT NULL,
    "name" VARCHAR(255) NOT NULL
);
CREATE INDEX "category_id_index" ON
    "category"("id");
CREATE INDEX "category_name_index" ON
    "category"("name");
ALTER TABLE
    "category" ADD PRIMARY KEY("id");
ALTER TABLE
    "category" ADD CONSTRAINT "category_name_unique" UNIQUE("name");
CREATE TABLE "customer"(
    "id" bigserial NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "surname" VARCHAR(255) NOT NULL,
    "email" VARCHAR(255) NOT NULL,
    "password" VARCHAR(255) NOT NULL,
    "role_id" INTEGER DEFAULT '1',
	"gender" VARCHAR(255) DEFAULT 'OTHER',
    "address" TEXT DEFAULT 'undefined',
    "phone_number" VARCHAR(255) DEFAULT '(012)-345-67-89',
    "enabled" BOOLEAN DEFAULT TRUE
);
CREATE INDEX "customer_id_index" ON
    "customer"("id");
CREATE INDEX "customer_email_index" ON
    "customer"("email");
CREATE INDEX "customer_name_surname_index" ON
    "customer"("name", "surname");
ALTER TABLE
    "customer" ADD PRIMARY KEY("id");
CREATE INDEX "customer_name_index" ON
    "customer"("name");
ALTER TABLE
    "customer" ADD CONSTRAINT "customer_email_unique" UNIQUE("email");
CREATE INDEX "customer_enabled_index" ON
    "customer"("enabled");
CREATE TABLE "menu"(
    "id" bigserial NOT NULL,
    "name" VARCHAR(255) NOT NULL
);
CREATE INDEX "menu_name_index" ON
    "menu"("name");
ALTER TABLE
    "menu" ADD PRIMARY KEY("id");
ALTER TABLE
    "menu" ADD CONSTRAINT "menu_name_unique" UNIQUE("name");
CREATE TABLE "employee_detail"(
    "id" bigserial NOT NULL,
    "employee_id" BIGINT NOT NULL,
    "salary" DECIMAL(8, 2) DEFAULT '0',
    "address" TEXT DEFAULT 'undefined',
    "gender" VARCHAR(255) DEFAULT 'OTHER',
    "phone_number" VARCHAR(255) DEFAULT '(012)-345-67-89',
	"enabled" BOOLEAN DEFAULT TRUE
);
CREATE INDEX "employee_detail_id_index" ON
    "employee_detail"("id");
CREATE INDEX "employee_detail_id_employee_id_index" ON
    "employee_detail"("id", "employee_id");
ALTER TABLE
    "employee_detail" ADD PRIMARY KEY("id");
CREATE INDEX "employee_detail_employee_id_index" ON
    "employee_detail"("employee_id");
CREATE TABLE "role"(
    "id" SERIAL NOT NULL,
    "name" VARCHAR(255) NOT NULL
);
CREATE INDEX "role_id_index" ON
    "role"("id");
ALTER TABLE
    "role" ADD PRIMARY KEY("id");
ALTER TABLE
    "role" ADD CONSTRAINT "role_name_unique" UNIQUE("name");
CREATE TABLE "employee_role"(
    "role_id" BIGINT NOT NULL,
    "employee_id" BIGINT NOT NULL
);
ALTER TABLE
    "employee_role" ADD CONSTRAINT "employee_role_role_id_employee_id_unique" UNIQUE("role_id", "employee_id");
CREATE INDEX "employee_role_role_id_employee_id_index" ON
    "employee_role"("role_id", "employee_id");
CREATE INDEX "employee_role_role_id_index" ON
    "employee_role"("role_id");
CREATE INDEX "employee_role_employee_id_index" ON
    "employee_role"("employee_id");
CREATE TABLE "reservation"(
    "id" bigserial NOT NULL,
    "customer_id" BIGINT NOT NULL,
    "reservation_date" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
    "count_of_guests" INTEGER DEFAULT '1',
    "table_number" INTEGER DEFAULT '1'
);
CREATE INDEX "reservation_customer_id_id_index" ON
    "reservation"("customer_id", "id");
CREATE INDEX "reservation_id_index" ON
    "reservation"("id");
ALTER TABLE
    "reservation" ADD PRIMARY KEY("id");
CREATE INDEX "reservation_customer_id_index" ON
    "reservation"("customer_id");
CREATE TABLE "cart"(
    "id" bigserial NOT NULL,
    "total_amount" DECIMAL(8, 2) NOT NULL,
    "customer_id" BIGINT NOT NULL
);
CREATE INDEX "cart_id_index" ON
    "cart"("id");
ALTER TABLE
    "cart" ADD PRIMARY KEY("id");
CREATE INDEX "cart_customer_id_index" ON
    "cart"("customer_id");
CREATE TABLE "product"(
    "id" bigserial NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "category_id" BIGINT NULL,
    "inventory" BIGINT DEFAULT '100',
    "price" DECIMAL(8, 2) NOT NULL,
    "description" TEXT DEFAULT 'undefined'
);
CREATE INDEX "product_id_index" ON
    "product"("id");
CREATE INDEX "product_name_index" ON
    "product"("name");
CREATE INDEX "product_name_category_id_index" ON
    "product"("name", "category_id");
ALTER TABLE
    "product" ADD PRIMARY KEY("id");
ALTER TABLE
    "product" ADD CONSTRAINT "product_name_unique" UNIQUE("name");
CREATE INDEX "product_category_id_index" ON
    "product"("category_id");
CREATE INDEX "product_price_index" ON
    "product"("price");
CREATE TABLE "cart_item"(
    "id" bigserial NOT NULL,
    "cart_id" BIGINT NOT NULL,
    "product_id" BIGINT NOT NULL,
    "unit_price" DECIMAL(8, 2) NOT NULL,
    "quantity" INTEGER NOT NULL,
    "total_price" DECIMAL(8, 2) NOT NULL
);
CREATE INDEX "cart_item_id_index" ON
    "cart_item"("id");
ALTER TABLE
    "cart_item" ADD PRIMARY KEY("id");
CREATE INDEX "cart_item_cart_id_index" ON
    "cart_item"("cart_id");
CREATE INDEX "cart_item_product_id_index" ON
    "cart_item"("product_id");
CREATE TABLE "menu_product"(
    "product_id" BIGINT NOT NULL,
    "menu_id" BIGINT NOT NULL
);
CREATE INDEX "menu_product_product_id_menu_id_index" ON
    "menu_product"("product_id", "menu_id");
ALTER TABLE
    "menu_product" ADD CONSTRAINT "menu_product_product_id_menu_id_unique" UNIQUE("product_id", "menu_id");
CREATE INDEX "menu_product_product_id_index" ON
    "menu_product"("product_id");
CREATE INDEX "menu_product_menu_id_index" ON
    "menu_product"("menu_id");
CREATE TABLE "image"(
    "id" bigserial NOT NULL,
    "file_name" TEXT NULL,
    "file_type" TEXT NULL,
    "image" bytea NULL,
    "product_id" BIGINT NOT NULL
);
CREATE INDEX "image_id_index" ON
    "image"("id");
ALTER TABLE
    "image" ADD PRIMARY KEY("id");
ALTER TABLE
    "image" ADD CONSTRAINT "image_file_name_unique" UNIQUE("file_name");
CREATE INDEX "image_product_id_index" ON
    "image"("product_id");
CREATE TABLE "order"(
    "id" bigserial NOT NULL,
    "customer_id" BIGINT NOT NULL,
    "order_status" VARCHAR(255) NULL,
    "order_date" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
    "total_amount" DECIMAL(8, 2) NOT NULL
);
CREATE INDEX "order_id_index" ON
    "order"("id");
ALTER TABLE
    "order" ADD PRIMARY KEY("id");
CREATE INDEX "order_customer_id_index" ON
    "order"("customer_id");
CREATE TABLE "order_item"(
    "id" bigserial NOT NULL,
    "product_id" BIGINT NOT NULL,
    "order_id" BIGINT NOT NULL,
    "quantity" INTEGER NOT NULL,
    "price" DECIMAL(8, 2) NOT NULL
);
CREATE INDEX "order_item_id_index" ON
    "order_item"("id");
ALTER TABLE
    "order_item" ADD PRIMARY KEY("id");
CREATE INDEX "order_item_product_id_index" ON
    "order_item"("product_id");
CREATE INDEX "order_item_order_id_index" ON
    "order_item"("order_id");
ALTER TABLE
    "cart_item" ADD CONSTRAINT "cart_item_cart_id_foreign" FOREIGN KEY("cart_id") REFERENCES "cart"("id");
ALTER TABLE
    "menu_product" ADD CONSTRAINT "menu_product_menu_id_foreign" FOREIGN KEY("menu_id") REFERENCES "menu"("id");
ALTER TABLE
    "employee_role" ADD CONSTRAINT "employee_role_employee_id_foreign" FOREIGN KEY("employee_id") REFERENCES "employee"("id");
ALTER TABLE
    "reservation" ADD CONSTRAINT "reservation_customer_id_foreign" FOREIGN KEY("customer_id") REFERENCES "customer"("id");
ALTER TABLE
    "role" ADD CONSTRAINT "role_id_foreign" FOREIGN KEY("id") REFERENCES "customer"("id");
ALTER TABLE
    "cart" ADD CONSTRAINT "cart_customer_id_foreign" FOREIGN KEY("customer_id") REFERENCES "customer"("id");
ALTER TABLE
    "menu_product" ADD CONSTRAINT "menu_product_product_id_foreign" FOREIGN KEY("product_id") REFERENCES "product"("id");
ALTER TABLE
    "order_item" ADD CONSTRAINT "order_item_product_id_foreign" FOREIGN KEY("product_id") REFERENCES "product"("id");
ALTER TABLE
    "employee_role" ADD CONSTRAINT "employee_role_role_id_foreign" FOREIGN KEY("role_id") REFERENCES "role"("id");
ALTER TABLE
    "product" ADD CONSTRAINT "product_category_id_foreign" FOREIGN KEY("category_id") REFERENCES "category"("id");
ALTER TABLE
    "employee_detail" ADD CONSTRAINT "employee_detail_employee_id_foreign" FOREIGN KEY("employee_id") REFERENCES "employee"("id");
ALTER TABLE
    "image" ADD CONSTRAINT "image_product_id_foreign" FOREIGN KEY("product_id") REFERENCES "product"("id");
ALTER TABLE
    "order_item" ADD CONSTRAINT "order_item_order_id_foreign" FOREIGN KEY("order_id") REFERENCES "order"("id");
ALTER TABLE
    "order" ADD CONSTRAINT "order_customer_id_foreign" FOREIGN KEY("customer_id") REFERENCES "customer"("id");
ALTER TABLE
    "cart_item" ADD CONSTRAINT "cart_item_product_id_foreign" FOREIGN KEY("product_id") REFERENCES "product"("id");