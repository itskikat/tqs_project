enum AuthorityType {
    CLIENT, BUSINESS, PROVIDER
}

interface Authority {
    authority: AuthorityType
}

export interface User{
    email: String,
    name: String,
    type: Authority
}