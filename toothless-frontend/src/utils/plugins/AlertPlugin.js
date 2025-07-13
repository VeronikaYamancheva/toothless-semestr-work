import { createApp, h, ref } from 'vue'
import AppAlert from "@/presentations/components/alert/AppAlert.vue";

export default {
    install(app) {
        const alertRef = ref(null)

        const mountAlert = () => {
            const container = document.createElement('div')
            document.body.appendChild(container)
            const vm = createApp({
                render() {
                    return h(AppAlert, {
                        ref: alertRef
                    })
                }
            })
            vm.mount(container)
        }

        const alert = (message) => {
            if (!alertRef.value) mountAlert()
            setTimeout(() => {
                alertRef.value?.show(message)
            }, 0)
        }

        app.config.globalProperties.$alert = alert
        app.provide('alert', alert)
    }
}
