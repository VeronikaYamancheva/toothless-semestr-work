export default {
    mounted(el, binding) {
        const handler = async (e) => {
            e.preventDefault();

            if (el.disabled) return;

            el.disabled = true;
            try {
                await binding.value();
            } catch (e) {
                console.error(e);
            } finally {
                el.disabled = false;
            }
        };

        el.__submitHandler__ = handler;
        el.addEventListener('click', handler);
    },
    unmounted(el) {
        el.removeEventListener('click', el.__submitHandler__);
        delete el.__submitHandler__;
    }
}
