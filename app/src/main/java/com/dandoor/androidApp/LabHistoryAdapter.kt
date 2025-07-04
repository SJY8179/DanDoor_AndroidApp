package com.dandoor.androidApp //

import android.view.LayoutInflater //
import android.view.View //
import android.view.ViewGroup //
import android.widget.TextView //
import androidx.recyclerview.widget.RecyclerView // 많은 양의 데이터를 효율적으로 보여주는 목록 뷰입니다.
import com.dandoor.androidApp.database.Lab // 데이터베이스 테이블 구조를 정의한 Lab 클래스를 가져옵니다.
import com.dandoor.androidApp.R //
import java.text.SimpleDateFormat //
import java.util.* //

// RecyclerView에 Lab 데이터를 표시하기 위한 어댑터 클래스입니다.
// 생성자로 람다 함수(onLabClick)를 받아서, 아이템 클릭 시 수행할 동작을 외부(Activity)에서 정의할 수 있게 합니다.
class LabHistoryAdapter(
    private val onLabClick: (Lab) -> Unit
) : RecyclerView.Adapter<LabHistoryAdapter.LabViewHolder>() {

    // RecyclerView에 표시될 Lab 데이터 리스트를 저장하는 변수입니다.
    private var labList: List<Lab> = listOf()

    // ViewHolder: 각 아이템 뷰(item_lab_history.xml)의 구성요소(TextView 등)를 저장하고 있는 객체입니다.
    // 이 클래스를 사용하면 매번 findViewById를 호출할 필요가 없어져 성능이 향상됩니다.
    class LabViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // 아이템 뷰 내부의 TextView들을 ID로 찾아와 변수에 할당합니다.
        private val aliasTextView: TextView = view.findViewById(R.id.tv_lab_alias)
        private val dateTextView: TextView = view.findViewById(R.id.tv_lab_date)

        // Lab 객체 데이터를 실제 TextView에 채워넣는(바인딩하는) 함수입니다.
        fun bind(lab: Lab) {
            aliasTextView.text = lab.alias
            // Long 타입의 타임스탬프(createdAt)를 "yyyy-MM-dd HH:mm" 형식의 문자열로 변환하여 표시합니다.
            dateTextView.text = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .format(Date(lab.createdAt))
        }
    }

    // ViewHolder가 처음 생성될 때 호출됩니다. (레이아웃을 로드하는 역할)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabViewHolder {
        // item_lab_history.xml 레이아웃 파일을 메모리에 로드(인플레이트)하여 View 객체로 만듭니다.
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lab_history, parent, false)
        // 생성된 View를 기반으로 ViewHolder 객체를 만들어 반환합니다.
        return LabViewHolder(view)
    }

    // ViewHolder가 화면에 표시될 데이터와 연결(바인딩)될 때 호출됩니다.
    override fun onBindViewHolder(holder: LabViewHolder, position: Int) {
        // 현재 위치(position)에 해당하는 Lab 데이터를 리스트에서 가져옵니다.
        val currentLab = labList[position]
        // ViewHolder의 bind 함수를 호출하여 데이터를 뷰에 채웁니다.
        holder.bind(currentLab)
        // 아이템 뷰 전체에 클릭 리스너를 설정합니다.
        holder.itemView.setOnClickListener {
            // 클릭이 발생하면, 생성자에서 받아온 onLabClick 람다 함수를 실행합니다.
            // 이때 클릭된 Lab 객체(currentLab)를 파라미터로 넘겨줍니다.
            onLabClick(currentLab)
        }
    }

    // 데이터 리스트의 전체 크기를 반환합니다. RecyclerView가 몇 개의 아이템을 그려야 할지 알려줍니다.
    override fun getItemCount() = labList.size

    // 외부(Activity)에서 새로운 데이터 리스트를 어댑터에 전달하기 위한 함수입니다.
    fun submitList(labs: List<Lab>) {
        // 내부 리스트를 새로운 리스트로 교체합니다.
        labList = labs
        // 데이터가 변경되었음을 RecyclerView에 알려 화면을 새로 그리도록 합니다.
        notifyDataSetChanged()
    }
}
