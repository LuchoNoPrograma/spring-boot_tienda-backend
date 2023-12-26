import {ref} from "vue";
import axiosServices from "@/utils/axios";
import type {AxiosRequestConfig} from "axios";

/*type config = {
  url: string,
  data: () => void
}*/

export const useAxios = () => {
  const loading = ref(true);
  const error = ref();
  const data = ref();
  const status = ref();

  const execute = async<T>(url: string, config?: AxiosRequestConfig,
                         onSuccess?: (data: T) => void, onRejected?: (data: any) => void) => {
    try {
      const response = await axiosServices(url, config);
      status.value = response.status;
      data.value = response.data;
      if (onSuccess) {
        onSuccess(response.data);
      }
    } catch (e) {
      console.log(e);
      error.value = true;
      if (onRejected) {
        onRejected(e);
      }
    } finally {
      error.value = false;
      loading.value = false;
    }
  }

  return {loading, error, data, status, execute}
}