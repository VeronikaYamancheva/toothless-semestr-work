import router from '@/router'

class ErrorHandler {
    constructor() {
        this.handlers = {
            401: this.handleUnauthorized,
            403: this.handleForbidden,
            404: this.handleNotFound,
            500: this.handleServerError,
            network: this.handleNetworkError,
            default: this.handleDefaultError
        }
    }

    handle(error) {
        console.error('[ErrorHandler] Ошибка:', error)

        const status = this._getErrorType(error)
        const handler = this.handlers[status] || this.handlers.default

        handler.call(this, error)

        return Promise.reject(error)
    }

    _getErrorType(error) {
        if (error.code === 'ERR_NETWORK') return 'network'
        if (error.response?.status) return error.response.status
        return 'default'
    }

    handleUnauthorized(error) {
        console.log('[ErrorHandler] Обработка 401 ошибки')
    }

    handleForbidden(error) {
        const errorData = error.response?.data
        if (errorData?.errorCode === 'NO_REQUIRED_ROLE') {
            this._showNotification(errorData.exceptionMessage, 'error')
        } else {
            this._showNotification('Доступ запрещён', 'error')
        }
    }

    handleNotFound() {
        this._showNotification('Ресурс не найден', 'warning')
        router.push('/not-found')
    }

    handleServerError() {
        this._showNotification('Ошибка сервера. Попробуйте позже', 'error')
    }

    handleNetworkError() {
        this._showNotification('Проблемы с соединением', 'warning')
    }

    handleDefaultError(error) {
        const message = error.response?.data?.message ||
            error.message ||
            'Неизвестная ошибка'
        this._showNotification(message, 'error')
    }

    _showNotification(message, type) {
        console[type === 'error' ? 'error' : 'warn'](`[Notification] ${message}`)
    }
}

export const errorHandler = new ErrorHandler()