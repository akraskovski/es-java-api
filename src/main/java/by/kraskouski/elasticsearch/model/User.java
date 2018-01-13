package by.kraskouski.elasticsearch.model;

public class User {

    private String id;
    private String firstname;
    private String lastname;

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public static Builder getBuilder() {
        return new User().new Builder();
    }

    public final class Builder {

        private Builder() {
        }

        public Builder setId(final String id) {
            User.this.id = id;
            return this;
        }

        public Builder setFirstname(final String firstname) {
            User.this.firstname = firstname;
            return this;
        }

        public Builder setLastname(String lastname) {
            User.this.lastname = lastname;
            return this;
        }

        public User build() {
            return User.this;
        }
    }
}
