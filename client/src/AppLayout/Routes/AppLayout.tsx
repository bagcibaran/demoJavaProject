import type { ReactNode } from 'react'
import LanguageSwitcher from '../LanguageSwitcher.tsx'

type AppLayoutProps = {
    children: ReactNode
}

function AppLayout({ children }: AppLayoutProps) {
    return (
        <div className="min-h-screen">
            <div className="fixed right-4 top-4 z-50">
                <LanguageSwitcher />
            </div>

            {children}
        </div>
    )
}

export default AppLayout