import { jwtVerify } from 'jose';
import {useAuthStore} from "@/store/auth.store";

const PUBLIC_KEY_BASE64 = process.env.VUE_APP_JWT_PUBLIC_KEY;

function base64ToArrayBuffer(base64) {
    const binaryString = atob(base64);
    const len = binaryString.length;
    const bytes = new Uint8Array(len);
    for(let i = 0; i < len; i++) {
        bytes[i] = binaryString.charCodeAt(i);
    }
    return bytes.buffer;
}

async function getCryptoKey() {
    return window.crypto.subtle.importKey(
        'spki',
        base64ToArrayBuffer(PUBLIC_KEY_BASE64),
        {
            name: 'RSASSA-PKCS1-v1_5',
            hash: 'SHA-256',
        },
        false,
        ['verify']
    );
}

export async function verifyJwt(token) {
    try {
        const key = await getCryptoKey();
        const { payload } = await jwtVerify(token, key);
        return payload;
    } catch (e) {
        const authStore = useAuthStore()
        await authStore.refresh()

        return null;
    }
}
