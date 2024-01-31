package com.nulp.mobilepartsshop.api.v1.auth.dto.response;

public class AuthenticationResponse {

    private String jwtToken;

    public AuthenticationResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public AuthenticationResponse() {
    }

    public static AuthenticationResponseBuilder builder() {
        return new AuthenticationResponseBuilder();
    }

    public String getJwtToken() {
        return this.jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AuthenticationResponse)) return false;
        final AuthenticationResponse other = (AuthenticationResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$jwtToken = this.getJwtToken();
        final Object other$jwtToken = other.getJwtToken();
        if (this$jwtToken == null ? other$jwtToken != null : !this$jwtToken.equals(other$jwtToken)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AuthenticationResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $jwtToken = this.getJwtToken();
        result = result * PRIME + ($jwtToken == null ? 43 : $jwtToken.hashCode());
        return result;
    }

    public String toString() {
        return "AuthenticationResponse(jwtToken=" + this.getJwtToken() + ")";
    }

    public static class AuthenticationResponseBuilder {
        private String jwtToken;

        AuthenticationResponseBuilder() {
        }

        public AuthenticationResponseBuilder jwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
            return this;
        }

        public AuthenticationResponse build() {
            return new AuthenticationResponse(this.jwtToken);
        }

        public String toString() {
            return "AuthenticationResponse.AuthenticationResponseBuilder(jwtToken=" + this.jwtToken + ")";
        }
    }
}
