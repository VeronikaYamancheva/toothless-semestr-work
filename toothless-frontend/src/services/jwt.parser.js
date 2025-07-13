import { verifyJwt } from '@/utils/plugins/jwtVerifier';

export async function getUserFromToken(token) {
    const payload = await verifyJwt(token);

    if (!payload) return null;

    return {
        id: payload.sub,
        name: payload.name,
        roles: payload.roles,
        userId: payload.userId,
    };
}
