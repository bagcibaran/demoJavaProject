import { useTranslation } from 'react-i18next'

function LanguageSwitcher() {
    const { i18n } = useTranslation()

    const currentLanguage = i18n.language.startsWith('tr') ? 'tr' : 'en'

    return (
        <div className="flex gap-2">
            <button
                type="button"
                onClick={() => i18n.changeLanguage('tr')}
                className={`rounded-md px-3 py-1 text-sm ${
                    currentLanguage === 'tr'
                        ? 'bg-blue-600 text-white'
                        : 'border border-slate-300 text-slate-600'
                }`}
            >
                TR
            </button>

            <button
                type="button"
                onClick={() => i18n.changeLanguage('en')}
                className={`rounded-md px-3 py-1 text-sm ${
                    currentLanguage === 'en'
                        ? 'bg-blue-600 text-white'
                        : 'border border-slate-300 text-slate-600'
                }`}
            >
                EN
            </button>
        </div>
    )
}

export default LanguageSwitcher