export type LoginRequest = {
    email: string
    password: string
}

export type LoginResponse = {
    token: string
    message: string
}

export type RegisterRequest = {
    username: string
    email: string
    password: string
}

export type UserResponse = {
    id: number
    username: string
    email: string
}
