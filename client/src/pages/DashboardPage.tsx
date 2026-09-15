import {
    useEffect,
    useState,
    type FormEvent,
} from 'react'
import { useNavigate } from 'react-router-dom'
import { useTranslation } from 'react-i18next'
import {
    Check,
    Pencil,
    Trash2,
    X,
} from 'lucide-react'

import {
    createTask,
    deleteTask,
    getTasks,
    updateTask,
} from '../services/taskService'

import type { Task } from '../types/task'

function DashboardPage() {
    const { t } = useTranslation()
    const navigate = useNavigate()

    const [tasks, setTasks] = useState<Task[]>([])
    const [newTaskTitle, setNewTaskTitle] = useState('')
    const [editingTaskId, setEditingTaskId] = useState<number | null>(null)
    const [editingTitle, setEditingTitle] = useState('')
    const [loading, setLoading] = useState(true)
    const [creating, setCreating] = useState(false)
    const [error, setError] = useState(false)

    const handleLogout = () => {
        localStorage.removeItem('token')
        navigate('/login')
    }

    useEffect(() => {
        async function loadTasks() {
            try {
                const result = await getTasks()
                setTasks(result)
            } catch (error) {
                console.error('Task listesi alınamadı:', error)
                setError(true)
            } finally {
                setLoading(false)
            }
        }

        loadTasks()
    }, [])

    const handleCreateTask = async (
        event: FormEvent<HTMLFormElement>,
    ) => {
        event.preventDefault()

        if (!newTaskTitle.trim()) {
            return
        }

        try {
            setCreating(true)

            const createdTask = await createTask({
                title: newTaskTitle,
            })

            setTasks((currentTasks) => [
                ...currentTasks,
                createdTask,
            ])

            setNewTaskTitle('')
        } catch (error) {
            console.error('Task oluşturulamadı:', error)
        } finally {
            setCreating(false)
        }
    }

    const handleToggleTask = async (task: Task) => {
        try {
            const updatedTask = await updateTask(task.id, {
                title: task.title,
                isCompleted: !task.isCompleted,
            })

            setTasks((currentTasks) =>
                currentTasks.map((currentTask) =>
                    currentTask.id === updatedTask.id
                        ? updatedTask
                        : currentTask,
                ),
            )
        } catch (error) {
            console.error('Task durumu güncellenemedi:', error)
        }
    }

    const handleDeleteTask = async (id: number) => {
        try {
            await deleteTask(id)

            setTasks((currentTasks) =>
                currentTasks.filter((task) => task.id !== id),
            )
        } catch (error) {
            console.error('Task silinemedi:', error)
        }
    }

    const handleStartEdit = (task: Task) => {
        setEditingTaskId(task.id)
        setEditingTitle(task.title)
    }

    const handleCancelEdit = () => {
        setEditingTaskId(null)
        setEditingTitle('')
    }

    const handleSaveEdit = async (task: Task) => {
        if (!editingTitle.trim()) {
            return
        }

        try {
            const updatedTask = await updateTask(task.id, {
                title: editingTitle,
                isCompleted: task.isCompleted,
            })

            setTasks((currentTasks) =>
                currentTasks.map((currentTask) =>
                    currentTask.id === updatedTask.id
                        ? updatedTask
                        : currentTask,
                ),
            )

            handleCancelEdit()
        } catch (error) {
            console.error('Task güncellenemedi:', error)
        }
    }

    return (
        <main className="min-h-screen bg-slate-100 p-8">
            <div className="mx-auto max-w-6xl">
                <div className="flex items-center justify-between">
                    <div>
                        <h1 className="text-3xl font-bold text-slate-900">
                            {t('dashboard.title')}
                        </h1>

                        <p className="mt-2 text-slate-600">
                            {t('dashboard.welcome')}
                        </p>
                    </div>

                    <button
                        type="button"
                        onClick={handleLogout}
                        className="rounded-lg bg-red-600 px-4 py-2 font-medium text-white hover:bg-red-700"
                    >
                        {t('dashboard.logout')}
                    </button>
                </div>

                <section className="mt-8 rounded-xl bg-white p-6 shadow">
                    <h2 className="text-xl font-semibold text-slate-900">
                        {t('dashboard.tasksTitle')}
                    </h2>

                    <form
                        onSubmit={handleCreateTask}
                        className="mt-4 flex gap-3"
                    >
                        <input
                            type="text"
                            value={newTaskTitle}
                            onChange={(event) =>
                                setNewTaskTitle(event.target.value)
                            }
                            placeholder={t('dashboard.newTask')}
                            className="flex-1 rounded-lg border border-slate-300 px-3 py-2 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-200"
                        />

                        <button
                            type="submit"
                            disabled={creating}
                            className="rounded-lg bg-blue-600 px-4 py-2 font-medium text-white hover:bg-blue-700 disabled:opacity-50"
                        >
                            {t('dashboard.addTask')}
                        </button>
                    </form>

                    {loading && (
                        <p className="mt-4 text-slate-500">
                            {t('dashboard.loading')}
                        </p>
                    )}

                    {error && (
                        <p className="mt-4 text-red-600">
                            {t('dashboard.loadError')}
                        </p>
                    )}

                    {!loading && !error && tasks.length === 0 && (
                        <p className="mt-4 text-slate-500">
                            {t('dashboard.empty')}
                        </p>
                    )}

                    {!loading && !error && tasks.length > 0 && (
                        <ul className="mt-4 space-y-3">
                            {tasks.map((task) => (
                                <li
                                    key={task.id}
                                    className="flex items-center gap-3 rounded-lg border border-slate-200 p-4"
                                >
                                    <button
                                        type="button"
                                        onClick={() => handleToggleTask(task)}
                                        className="rounded-md border border-slate-300 p-2 hover:bg-slate-100"
                                    >
                                        {task.isCompleted ? (
                                            <Check className="h-4 w-4 text-green-600" />
                                        ) : (
                                            <X className="h-4 w-4 text-red-600" />
                                        )}
                                    </button>

                                    {editingTaskId === task.id ? (
                                        <input
                                            value={editingTitle}
                                            onChange={(event) =>
                                                setEditingTitle(event.target.value)
                                            }
                                            className="flex-1 rounded-lg border border-slate-300 px-3 py-2"
                                        />
                                    ) : (
                                        <span
                                            className={`flex-1 ${
                                                task.isCompleted
                                                    ? 'text-slate-400 line-through'
                                                    : 'text-slate-800'
                                            }`}
                                        >
                      {task.title}
                    </span>
                                    )}

                                    {editingTaskId === task.id ? (
                                        <>
                                            <button
                                                type="button"
                                                onClick={() => handleSaveEdit(task)}
                                                className="rounded-md p-2 text-green-600 hover:bg-green-50"
                                            >
                                                <Check className="h-4 w-4" />
                                            </button>

                                            <button
                                                type="button"
                                                onClick={handleCancelEdit}
                                                className="rounded-md p-2 text-slate-500 hover:bg-slate-100"
                                            >
                                                <X className="h-4 w-4" />
                                            </button>
                                        </>
                                    ) : (
                                        <button
                                            type="button"
                                            onClick={() => handleStartEdit(task)}
                                            className="rounded-md p-2 text-blue-600 hover:bg-blue-50"
                                        >
                                            <Pencil className="h-4 w-4" />
                                        </button>
                                    )}

                                    <button
                                        type="button"
                                        onClick={() => handleDeleteTask(task.id)}
                                        className="rounded-md p-2 text-red-600 hover:bg-red-50"
                                    >
                                        <Trash2 className="h-4 w-4" />
                                    </button>
                                </li>
                            ))}
                        </ul>
                    )}
                </section>
            </div>
        </main>
    )
}

export default DashboardPage