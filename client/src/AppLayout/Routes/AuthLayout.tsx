import type { ReactNode } from 'react'

type AuthLayoutProps = {
    children: ReactNode
}

function AuthLayout({ children }: AuthLayoutProps) {
    return (
        <main className="min-h-screen bg-slate-100 px-4 py-8">
            <div className="mx-auto flex min-h-[calc(100vh-4rem)] max-w-md flex-col justify-center">
                <section className="rounded-xl bg-red p-8 shadow-lg">
                    {children}
                </section>
            </div>
        </main>
    )
}

export default AuthLayout