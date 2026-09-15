import type {
    CreateTaskRequest,
    Task,
    UpdateTaskRequest,
} from '../types/task'

const API_URL = 'http://localhost:8080'

function getAuthHeaders(): HeadersInit {
    const token = localStorage.getItem('token')

    if (!token) {
        throw new Error('Oturum tokenı bulunamadı')
    }

    return {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
    }
}

export async function getTasks(): Promise<Task[]> {
    const response = await fetch(`${API_URL}/tasks`, {
        method: 'GET',
        headers: getAuthHeaders(),
    })

    if (!response.ok) {
        throw new Error('Task listesi alınamadı')
    }

    return response.json() as Promise<Task[]>
}

export async function createTask(
    request: CreateTaskRequest,
): Promise<Task> {
    const response = await fetch(`${API_URL}/tasks`, {
        method: 'POST',
        headers: getAuthHeaders(),
        body: JSON.stringify(request),
    })

    if (!response.ok) {
        throw new Error('Task oluşturulamadı')
    }

    return response.json() as Promise<Task>
}

export async function updateTask(
    id: number,
    request: UpdateTaskRequest,
): Promise<Task> {
    const response = await fetch(`${API_URL}/tasks/${id}`, {
        method: 'PUT',
        headers: getAuthHeaders(),
        body: JSON.stringify(request),
    })

    if (!response.ok) {
        throw new Error('Task güncellenemedi')
    }

    return response.json() as Promise<Task>
}

export async function deleteTask(id: number): Promise<void> {
    const response = await fetch(`${API_URL}/tasks/${id}`, {
        method: 'DELETE',
        headers: getAuthHeaders(),
    })

    if (!response.ok) {
        throw new Error('Task silinemedi')
    }
}