export type Task = {
    id: number
    title: string
    isCompleted: boolean
    username: string
}

export type CreateTaskRequest = {
    title: string
}

export type UpdateTaskRequest = {
    title: string
    isCompleted: boolean
}