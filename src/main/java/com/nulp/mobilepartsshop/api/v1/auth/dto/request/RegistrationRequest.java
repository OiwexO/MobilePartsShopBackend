package com.nulp.mobilepartsshop.api.v1.auth.dto.request;

public class RegistrationRequest {

    private String firstname;

    private String lastname;

    private String username;

    private String password;

    public RegistrationRequest(String firstname, String lastname, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    public RegistrationRequest() {
    }

    public static RegistrationRequestBuilder builder() {
        return new RegistrationRequestBuilder();
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof RegistrationRequest)) return false;
        final RegistrationRequest other = (RegistrationRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$firstname = this.getFirstname();
        final Object other$firstname = other.getFirstname();
        if (this$firstname == null ? other$firstname != null : !this$firstname.equals(other$firstname)) return false;
        final Object this$lastname = this.getLastname();
        final Object other$lastname = other.getLastname();
        if (this$lastname == null ? other$lastname != null : !this$lastname.equals(other$lastname)) return false;
        final Object this$username = this.getUsername();
        final Object other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof RegistrationRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $firstname = this.getFirstname();
        result = result * PRIME + ($firstname == null ? 43 : $firstname.hashCode());
        final Object $lastname = this.getLastname();
        result = result * PRIME + ($lastname == null ? 43 : $lastname.hashCode());
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        return result;
    }

    public String toString() {
        return "RegistrationRequest(firstname=" + this.getFirstname() + ", lastname=" + this.getLastname() + ", username=" + this.getUsername() + ", password=" + this.getPassword() + ")";
    }

    public static class RegistrationRequestBuilder {
        private String firstname;
        private String lastname;
        private String username;
        private String password;

        RegistrationRequestBuilder() {
        }

        public RegistrationRequestBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public RegistrationRequestBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public RegistrationRequestBuilder username(String username) {
            this.username = username;
            return this;
        }

        public RegistrationRequestBuilder password(String password) {
            this.password = password;
            return this;
        }

        public RegistrationRequest build() {
            return new RegistrationRequest(this.firstname, this.lastname, this.username, this.password);
        }

        public String toString() {
            return "RegistrationRequest.RegistrationRequestBuilder(firstname=" + this.firstname + ", lastname=" + this.lastname + ", username=" + this.username + ", password=" + this.password + ")";
        }
    }
}
