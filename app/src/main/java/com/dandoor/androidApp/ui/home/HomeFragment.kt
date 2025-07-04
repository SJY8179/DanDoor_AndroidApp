package com.dandoor.androidApp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dandoor.androidApp.databinding.FragmentHomeBinding

// 홈 화면을 제어하는 프래그먼트 클래스
class HomeFragment : Fragment() {

    // ViewBinding 인스턴스를 저장할 변수. _binding은 null을 허용하고, binding은 null을 허용하지 않음.
    // 이는 메모리 누수를 방지하기 위한 일반적인 패턴입니다.
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // 프래그먼트의 UI를 생성하고 반환하는 함수
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // XML 레이아웃을 인플레이트하고 바인딩 객체를 생성합니다.
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }



    // 프래그먼트 뷰가 파괴될 때 바인딩 객체를 해제하여 메모리 누수를 방지합니다.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
